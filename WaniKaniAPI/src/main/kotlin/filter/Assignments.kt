/*
* Description: Requesting current Assignments from API and filters by given Input
* */

package wanikaniAPI.filter

import kotlinx.coroutines.*
import wanikaniAPI.Assignment
import wanikaniAPI.API.*
import kotlin.collections.ArrayList


class Assignments(service: WaniKaniService): AFilter(service = service) {

    private var needsUpdate = true
    private var shutDown = false
    private var reviews: Array<Assignment>? = null
    private var lessons: Array<Assignment>? = null
    override var scope: CoroutineScope = CoroutineScope(newSingleThreadContext("AssigneMentThread"))

    init {
        scope.launch { updater() }
    }

    enum class Type{ LESSON, REVIEW }
    enum class AssignmentContent{ALL,LESSON,REVIEW}

    fun getNumberOf(type: AssignmentContent): Int {
        return when(type) {
            AssignmentContent.LESSON->lessons!!.size
            AssignmentContent.REVIEW->reviews!!.size
            AssignmentContent.ALL->lessons!!.size + reviews!!.size
        }
    }

    fun updateLessons() {
        lessons = getCurrentAssignments(AssignmentContent.LESSON)
    }
    //
    //
    fun getCurrent(type: AssignmentContent): Array<Assignment>? {
        return when(type) {
            AssignmentContent.LESSON->lessons
            AssignmentContent.REVIEW->reviews
            AssignmentContent.ALL-> (lessons!!.toList() + reviews!!.toList()).toTypedArray()
        }
    }

    private fun getArrayOfAssignments(data: Array<Ressource<Assignment>?>?) : ArrayList<Assignment>? {
        if(data == null)
            return null
        var res: ArrayList<Assignment> = ArrayList()
        for(i in 0 until data!!.size) {
            res.add(data[i]!!.data)
        }
        return res
    }

    private fun getCurrentAssignments(contentType: AssignmentContent): Array<Assignment>? {
        //
        // Getting current AssgnmentCollection
        var assignmentCollection = service.getAssignments(contentType)
        //
        // remove reouscre meta Data forr return type
        val res = getArrayOfAssignments(assignmentCollection.data)
        //
        // Check if more Assignments are available
        while(assignmentCollection.meta?.page?.next_url != null) {
            val pageUrl = assignmentCollection.meta!!.page.next_url!!
            assignmentCollection = service.getAssignments(contentType, pageUrl)
            res!!.addAll(getArrayOfAssignments(assignmentCollection.data)!!)
        }
        return res?.toTypedArray()
    }

    override suspend fun updater() {

        while(!shutDown) {
            if(needsUpdate) {
                //
                // Get current availabe Lessons
                val LesseonRequest = GlobalScope.async(Dispatchers.IO) {
                    getCurrentAssignments(AssignmentContent.LESSON)
                }
                //
                // Get current available reviews
                val reviewRequest =  GlobalScope.async(Dispatchers.IO) {
                    getCurrentAssignments(AssignmentContent.REVIEW)
                }
                lessons = LesseonRequest.await()
                reviews = reviewRequest.await()
                needsUpdate = false
            }
        }
    }

}