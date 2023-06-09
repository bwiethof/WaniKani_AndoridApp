package model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Resource<T>(
    val id: Int?,
    @SerialName("object") val obj: String,
    val url: String,
    val data_updated_at: String,
    val data: T
)

@Serializable
data class Report<T>(
    val data_updated_at: String,
    val url: String,
    @SerialName("object") val obj: String,
    val data: T
)

@Serializable
data class Collection<T>(
    @SerialName("object") val obj: String,
    val url: String,
    val pages: Page,
    val total_count: Int,
    val data_updated_at: String?,
    val data: List<Resource<T>>?
) {
    init {
        require(obj == "collection")
    }

    @Serializable
    data class Page(val per_page: Int?, val next_url: String?, val previous_url: String?)
}
