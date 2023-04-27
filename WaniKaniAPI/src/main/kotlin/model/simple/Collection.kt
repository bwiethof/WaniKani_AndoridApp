package model.simple

typealias WanikaniCollection<T> = model.Collection<T>

data class Collection<T>(val metaData: MetaData, val data: List<Resource<T>>?) {

    data class MetaData(
        val obj: String,
        val url: String,
        val pages: model.Collection.Page,
        val totalCount: Int,
        val dataUpdatedAt: String?,
    )

    companion object {
        fun <T> fromWanikani(collection: WanikaniCollection<T>): Collection<T> = with(collection) {
            Collection(MetaData(obj, url, pages, total_count, data_updated_at), data?.map { Resource.fromWanikani(it) })
        }

        fun <T> toWanikani(collection: Collection<T>): WanikaniCollection<T> = with(collection) {
            WanikaniCollection(metaData.obj,
                metaData.url,
                metaData.pages,
                metaData.totalCount,
                metaData.dataUpdatedAt,
                data?.map { Resource.toWanikani(it) })
        }
    }
}