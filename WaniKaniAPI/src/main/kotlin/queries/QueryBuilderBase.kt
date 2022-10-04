package queries

import queries.filters.ParameterComposition
import queries.filters.ParameterType

abstract class QueryBuilderBase(private val acceptedQueryParams: List<ParameterType>) {
    abstract var route: String
        protected set

    var filterParameterMap: Map<String, String>? = null
        private set

    private var condition: ParameterComposition? = null

    fun build() {
        filterParameterMap = condition?.build() ?: emptyMap()
    }

    fun with(initializer: ParameterComposition.() -> Unit) {
        condition = ParameterComposition(acceptedQueryParams).apply(initializer)
    }
}