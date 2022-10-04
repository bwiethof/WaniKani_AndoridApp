package queries


private const val levelProgressionRoute = "level_progressions"
private const val idsParam = "ids"
private const val updatedAfterParam = "updated_after"

class LevelProgressionQueryBuilder: CollectionQueryBuilder() {
    override var route: String = levelProgressionRoute
}
