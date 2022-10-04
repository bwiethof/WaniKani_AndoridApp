package queries

import queries.filters.ParameterComposition

class GeneralQueryBuilder {
    private var queryBuilderImpl: QueryBuilderBase? = null

    fun from(resource: Resource): QueryBuilderBase {
        queryBuilderImpl = resource.getBuilder()
        return queryBuilderImpl as QueryBuilderBase
    }

    fun where(initializer: ParameterComposition.() -> Unit) {
        if (queryBuilderImpl === null)
            throw IllegalStateException("Cannot apply resource filter without resource")
        queryBuilderImpl?.with(initializer)
    }

    fun build(): QueryBuilderBase {
        queryBuilderImpl?.build()
        return queryBuilderImpl as QueryBuilderBase
    }
}

fun query(initializer: GeneralQueryBuilder.() -> Unit): GeneralQueryBuilder {
    return GeneralQueryBuilder().apply(initializer)
}
