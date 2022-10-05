package queries

import queries.filters.ParameterComposition
import queries.filters.ParameterType

abstract class QueryBuilderBase(private val acceptedQueryParams: List<ParameterType>) {
    abstract var route: String
        protected set
    var specificResourceId: Int? = null

    var filterParameterMap: Map<String, String>? = null
        private set

    private var condition: ParameterComposition? = null

    private fun specificResourceRequested() = specificResourceId !== null

    fun build() {
        if (specificResourceRequested())
            route += "/$specificResourceId"
        else
            filterParameterMap = condition?.build() ?: emptyMap()

        if(specificResourceRequested() && condition !== null)
            println("Filtering for specific resources is not possible. Ignoring filtering parameters")
    }

    fun where(initializer: ParameterComposition.() -> Unit) {
        condition = ParameterComposition(acceptedQueryParams).apply(initializer)
    }
}