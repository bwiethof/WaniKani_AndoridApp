package queries


interface SubQueryBuilder {
    fun build(): Map<String, String>
}

abstract class QueryBuilder {
    abstract var route: String
        protected set
    val parameters = mutableMapOf<String, String>()
    private val subQueryBuilders = mutableListOf<SubQueryBuilder>()
    private var specificID: Int? = null


    protected fun registerSubQueryBuilder(subQueryBuilder: SubQueryBuilder) {
        subQueryBuilders.add(subQueryBuilder)
    }
    open fun withSpecificId(id: Int): QueryBuilder {
        specificID = id
        return this
    }

    private fun wantsSpecific() = specificID !== null

    abstract fun buildInternal(): QueryBuilder

    fun build(): QueryBuilder {
        buildInternal()

        subQueryBuilders.forEach { it -> parameters.putAll(it.build()) }

        if (specificID != null && parameters.isNotEmpty())
            throw IllegalStateException("Cannot build Query with specific id and search parameters.")

        if (wantsSpecific())
            route += "/" + specificID.toString()

        return this
    }
}

