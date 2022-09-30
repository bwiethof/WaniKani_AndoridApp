package queries

abstract class QueryBuilder {
    abstract var route: String
        protected set
    val parameters = mutableMapOf<String, String>()
    private var specificID: Int? = null

    open fun withSpecificId(id: Int): QueryBuilder {
        specificID = id
        return this
    }

    abstract fun buildInternal(): QueryBuilder
    fun build(): QueryBuilder {
        buildInternal()

        if (specificID != null && parameters.isNotEmpty())
            throw IllegalStateException("Cannot build Query with specific id and search parameters.")

        if (specificID !== null)
            route += "/" + specificID.toString()

        return this
    }
}

