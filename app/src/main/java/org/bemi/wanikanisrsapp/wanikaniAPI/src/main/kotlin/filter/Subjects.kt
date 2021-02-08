package wanikaniAPI.filter

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.newSingleThreadContext
import wanikaniAPI.*
import wanikaniAPI.API.WaniKaniService

class Subjects(service: WaniKaniService): AFilter(service) {
    override var scope: CoroutineScope = CoroutineScope(newSingleThreadContext("Subjects"))

    enum class SubjectType{
        RADICAL, KANJI, VOCABULARY, ALL
    }

    override suspend fun updater() {


    }

    fun <T: ISubject> getSubjects(typeOfT: SubjectType, level: Int = 60, subjectIds: Array<Int> = ArrayList<Int>(0).toTypedArray()): Array<Subject<T>>?  {

        return when(typeOfT) {
            SubjectType.RADICAL -> {
               null
            }
            SubjectType.KANJI->{
                null
            }
            SubjectType.VOCABULARY->{
                null
            }
            SubjectType.ALL-> {
                null
            }
        }
    }

    private fun getRadicals(subjectIds: Array<Int> = ArrayList<Int>(0).toTypedArray()) {

    }

    private fun getKanji(subjectIds: Array<Int> = ArrayList<Int>(0).toTypedArray()) {

    }

    private fun getVocabulary(subjectIds: Array<Int> = ArrayList<Int>(0).toTypedArray()) {

    }

    private fun getAll(subjectIds: Array<Int> = ArrayList<Int>(0).toTypedArray()) {

    }

}