package queries

import models.Subject

private const val reviewStatisticsRoute = "review_statistics"
private const val percentageGreaterThanParam = "percentages_greater_than"
private const val percentageLessThanParam = "percentages_less_than"
private const val subjectIdsParam = "subject_ids"
private const val subjectTypesParam = "subject_types"

class ReviewStatisticsQueryBuilder : CollectionQueryBuilder() {
    override var route = reviewStatisticsRoute

    private var hidden: Boolean? = null
    private var subjectIds: List<Int>? = null
    private var subjectTypes: MutableList<Subject.Type>? = null

    private var percentageGreaterThan: Int? = null
    private var percentageLessThan: Int? = null


    fun withPercentageGreaterThan(percentage: Int): ReviewStatisticsQueryBuilder {
        percentageGreaterThan = percentage
        return this
    }

    fun withPercentageLessThan(percentage: Int): ReviewStatisticsQueryBuilder {
        percentageLessThan = percentage
        return this
    }

    override fun buildInternal(): QueryBuilder {
        super.buildInternal()

        return this
    }
}