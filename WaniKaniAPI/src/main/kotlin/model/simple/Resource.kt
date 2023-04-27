package model.simple

typealias WanikaniResource<T> = model.Resource<T>

data class Resource<T>(
    val metaData: MetaData,
    val data: T
) {

    data class MetaData(
        val id: Int?,
        val obj: String,
        val url: String,
        val dataUpdatedAt: String
    )

    companion object {
        fun <T> fromWanikani(resource: WanikaniResource<T>): Resource<T> = with(resource) {
            Resource(MetaData(id, obj, url, data_updated_at), resource.data)
        }

        fun <T> toWanikani(resource: Resource<T>): WanikaniResource<T> = with(resource) {
            WanikaniResource(metaData.id, metaData.obj, metaData.url, metaData.dataUpdatedAt, data)
        }
    }
}

