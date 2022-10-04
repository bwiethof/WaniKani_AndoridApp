package queries

private const val reviewRoute = "reviews"
private const val assignmentIdsParam = "assignment_ids"
private const val subjectIdsParam = "subject_ids"

class ReviewQueryBuilder : CollectionQueryBuilder() {
    override var route: String = reviewRoute
    private var assignmentIds: List<Int>? = null
    private var subjectIds: List<Int>? = null

    fun withAssignmentIds(ids: List<Int>): CollectionQueryBuilder {
        this.assignmentIds = addToList(this.assignmentIds, ids)
        return this
    }

    fun withAssignmentId(id: Int) = withAssignmentIds(listOf(id))

    fun withSubjectIds(ids: List<Int>): CollectionQueryBuilder {
        this.assignmentIds = addToList(this.assignmentIds, ids)
        return this
    }

    fun withSubjectId(id: Int) = withSubjectIds(listOf(id))


    override fun buildInternal(): QueryBuilder {
        super.buildInternal()

        assignmentIds?.let { parameters.put(assignmentIdsParam, it.joinToString(",")) }
        subjectIds?.let {
            parameters.put(subjectIdsParam, it.joinToString(","))
        }

        return this
    }

}