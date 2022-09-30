package queries

private const val summaryRoute = "summary"

class SummaryQueryBuilder() : QueryBuilder() {
    override var route: String = summaryRoute
    override fun buildInternal(): QueryBuilder {
        return this
    }

    override fun withSpecificId(id: Int): QueryBuilder {
        // Summaries does not have an ID
        return this
    }
}