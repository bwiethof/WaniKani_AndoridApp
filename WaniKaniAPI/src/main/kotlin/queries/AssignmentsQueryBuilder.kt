package queries

import models.Date
import models.Subject

private const val assignmentRoute = "assignments"
private const val availableAfterParam = "available_after"
private const val availableBeforeParam = "available_before"
private const val burnedParam = "burned"
private const val hiddenParam = "hidden"
private const val idsParam = "ids"
private const val immediatelyAvailableForLessonsParam = "immediately_available_for_lessons"
private const val immediatelyAvailableForReviewParam = "immediately_available_for_review"
private const val inReviewParam = "in_review"
private const val levelsParam = "levels"
private const val startedParam = "started"
private const val subjectIdsParam = "subject_ids"
private const val subjectTypesParam = "subject_types"
private const val unlockedParam = "unlocked"
private const val updatedAfterParam = "updated_after"

// TODO: implement logic for assignments level 1->UserLevel
// ref: https://docs.api.wanikani.com/20170710/#get-all-assignments

class AssignmentsQueryBuilder : CollectionQueryBuilder() {
    override var route: String = assignmentRoute

    private var availableAfter: Date? = null
    private var availableBefore: Date? = null
    private var burned: Boolean? = null
    private var hidden: Boolean? = null
    private var immediatelyAvailableForLessons: Boolean? = null
    private var immediatelyAvailableForReview: Boolean? = null
    private var inReview: Boolean? = null
    private var levels: List<Int>? = null
    private var started: Boolean? = null
    private var subjectIds: List<Int>? = null
    private var subjectTypes: MutableList<Subject.Type>? = null
    private var unlocked: Boolean? = null

    override fun buildInternal(): QueryBuilder {
        availableAfter?.let { parameters.put(availableAfterParam, availableAfter!!) }
        burned?.let { parameters.put(burnedParam, burned.toString()) }
        hidden?.let { parameters.put(hiddenParam, hidden.toString()) }
        immediatelyAvailableForLessons?.let {
            if (immediatelyAvailableForLessons!!)
                parameters[immediatelyAvailableForLessonsParam] = ""
        }
        immediatelyAvailableForReview?.let {
            if (immediatelyAvailableForReview!!)
                parameters[immediatelyAvailableForReviewParam] = ""
        }
        inReview?.let { parameters.put(inReviewParam, inReview.toString()) }
        levels?.let { parameters.put(levelsParam, levels!!.joinToString(",")) }
        started?.let { parameters.put(startedParam, started.toString()) }
        subjectIds?.let { parameters.put(subjectIdsParam, subjectIds!!.joinToString(",")) }
        subjectTypes?.let {
            parameters.put(
                subjectTypesParam,
                subjectTypes!!.joinToString { type: Subject.Type -> type.toString() })
        }
        unlocked?.let { parameters.put(unlockedParam, unlocked.toString()) }

        return this
    }

    fun isAvailableAfter(availableAfter: Date): AssignmentsQueryBuilder {
        this.availableAfter = availableAfter
        return this
    }

    fun isAvailableBefore(availableBefore: Date): AssignmentsQueryBuilder {
        this.availableBefore = availableBefore
        return this
    }

    fun wasBurned(burned: Boolean): AssignmentsQueryBuilder {
        this.burned = burned
        return this
    }

    fun isHidden(hidden: Boolean): AssignmentsQueryBuilder {
        this.hidden = hidden
        return this
    }

    fun isImmediatelyAvailableForLessons(immediatelyAvailableForLessons: Boolean): AssignmentsQueryBuilder {
        this.immediatelyAvailableForLessons = immediatelyAvailableForLessons
        return this
    }

    fun isImmediatelyAvailableForReview(immediatelyAvailableForReview: Boolean): AssignmentsQueryBuilder {
        this.immediatelyAvailableForReview = immediatelyAvailableForReview
        return this
    }

    fun isInReview(inReview: Boolean): AssignmentsQueryBuilder {
        this.inReview = inReview
        return this
    }

    fun withLevels(levels: List<Int>): AssignmentsQueryBuilder {
        this.levels = if (this.levels.isNullOrEmpty()) levels else this.levels!!.plus(levels)
        return this
    }

    fun withLevel(level: Int): AssignmentsQueryBuilder = this.withLevels(listOf(level))

    fun isStarted(started: Boolean): AssignmentsQueryBuilder {
        this.started = started
        return this
    }

    fun withSubjectIds(subjectIds: List<Int>): AssignmentsQueryBuilder {
        this.subjectIds =
            if (this.subjectIds.isNullOrEmpty()) subjectIds else this.subjectIds!!.plus(subjectIds)
        return this
    }

    fun withSubjectId(id: Int): AssignmentsQueryBuilder = this.withSubjectIds(listOf(id))

    fun withSubjectTypes(subjectTypes: List<Subject.Type>): AssignmentsQueryBuilder {
        this.subjectTypes =
            if (this.subjectTypes.isNullOrEmpty()) subjectTypes.toMutableList() else this.subjectTypes!!.addIfNotExist(
                subjectTypes
            )

        return this
    }

    fun withSubjectType(type: Subject.Type): AssignmentsQueryBuilder =
        withSubjectTypes(listOf(type))

    fun isUnlocked(unlocked: Boolean): AssignmentsQueryBuilder {
        this.unlocked = unlocked
        return this
    }
}

