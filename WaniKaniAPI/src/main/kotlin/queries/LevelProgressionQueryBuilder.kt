package queries


private const val levelProgressionRoute = "level_progressions"
private const val idsParam = "ids"
private const val updatedAfterParam = "updated_after"
private const val specificParam = "id"

class LevelProgressionQueryBuilder: QueryBuilder() {
    override var route: String = levelProgressionRoute
    private var ids: List<Int>? =null
    private var updatedAfter: String? = null


    fun withIds(ids: List<Int>) : LevelProgressionQueryBuilder {
        this.ids = addToList(this.ids,ids)
        return this
    }

    fun updatedAfter(updatedAfter: String): LevelProgressionQueryBuilder {
        this.updatedAfter = updatedAfter
        return this
    }


    override fun buildInternal(): QueryBuilder {

        if (updatedAfter != null) this.parameters[updatedAfterParam] = updatedAfter!!

        if (!ids.isNullOrEmpty()) this.parameters[idsParam] = ids!!.joinToString(",")

        return this
    }
}
