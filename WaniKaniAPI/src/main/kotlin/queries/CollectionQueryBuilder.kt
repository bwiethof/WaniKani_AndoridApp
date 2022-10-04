package queries


private const val updatedAfterParam = "updated_after"
private const val idsParam = "ids"


interface CollectionQueryBuilderInterface {
    fun withIds(ids: List<Int>): QueryBuilder
    fun updatedAfter(updatedAfter: String): QueryBuilder
}

class CollectionSubQueryImpl: SubQueryBuilder {
    private var updatedAfter: models.Date? = null
    private var ids: List<Int>? = null

    fun withIds(ids: List<Int>) {
        this.ids = addToList(this.ids, ids)
    }
    fun updatedAfter(updatedAfter: String) {
        this.updatedAfter = updatedAfter
    }

    override fun build(): Map<String, String> {
        val params = mutableMapOf<String, String>()

        updatedAfter?.let { params.put(updatedAfterParam, it) }
        ids?.let { params.put(idsParam, it.joinToString(",")) }

        return params
    }
}


abstract class CollectionQueryBuilder : QueryBuilder() {
    private var updatedAfter: models.Date? = null
    private var ids: List<Int>? = null

    fun withIds(ids: List<Int>): CollectionQueryBuilder {
        this.ids = addToList(this.ids, ids)
        return this
    }


    fun updatedAfter(updatedAfter: String): CollectionQueryBuilder {
        this.updatedAfter = updatedAfter
        return this
    }


    override fun buildInternal(): QueryBuilder {
        updatedAfter?.let { parameters.put(updatedAfterParam, it) }
        ids?.let { parameters.put(idsParam, it.joinToString(",")) }

        return this
    }
}

