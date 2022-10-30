package queries

import queries.filters.ParameterComposition

class QueryBuilder {
    private var queryBuilderImpl: QueryBuilderBase? = null

    fun from(resource: Resource) {
        queryBuilderImpl = resource.getBuilder()
    }

    fun where(initializer: ParameterComposition.() -> Unit) {
        if (queryBuilderImpl?.where(initializer) === null)
            throw IllegalStateException("Cannot apply resource filter without specifying type")
    }

    fun matches(id: Int) {
        if (queryBuilderImpl?.let { it.specificResourceId = id } === null)
            throw IllegalStateException("Cannot request specific resource without specifying type")
    }

    fun build(): QueryBuilderBase? = queryBuilderImpl?.apply { build() }
}

fun query(initializer: QueryBuilder.() -> Unit): QueryBuilder = QueryBuilder().apply(initializer)

