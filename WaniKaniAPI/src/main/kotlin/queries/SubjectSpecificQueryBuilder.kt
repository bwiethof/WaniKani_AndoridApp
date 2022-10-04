package queries

import models.Subject


private const val subjectIdsParam = "subject_ids"
private const val subjectTypesParam = "subject_types"

interface SubjectFilterQueryBuilder {
    fun withSubjectIds(subjectIds: List<Int>): QueryBuilder
    fun withSubjectId(subjectId: Int) = withSubjectIds(listOf(subjectId))
    fun withSubjectTypes(subjectTypes: List<Subject.Type>): QueryBuilder
    fun withSubjectType(subjectType: Subject.Type) = withSubjectTypes(listOf(subjectType))
}


class SubjectSubQueryImpl : SubQueryBuilder {
    var subjectIds: List<Int>? = null
    var subjectTypes: MutableList<Subject.Type>? = null

    fun withSubjectIds(subjectIds: List<Int>) {
        this.subjectIds =
            if (this.subjectIds.isNullOrEmpty()) subjectIds else this.subjectIds!!.plus(subjectIds)
    }

    fun withSubjectId(id: Int) = this.withSubjectIds(listOf(id))

    fun withSubjectTypes(subjectTypes: List<Subject.Type>) {
        if (this.subjectTypes?.apply { this.addIfNotExist(subjectTypes) } === null)
            subjectTypes.toMutableList()
    }

    fun withSubjectType(type: Subject.Type) = withSubjectTypes(listOf(type))
    override fun build(): Map<String, String> {
        val params = mutableMapOf<String, String>()
        subjectIds?.let { params.put(subjectIdsParam, it.joinToString(",")) }
        subjectTypes?.let {
            params.put(
                subjectTypesParam,
                it.joinToString { type: Subject.Type -> type.toString() })
        }
        return params
    }
}