package model

import kotlinx.serialization.Serializable

@Serializable
data class Summary(
    val lessons: List<Item>,
    val next_reviews_at: String?,
    val reviews: List<Item>
) {
    @Serializable
    data class Item(val available_at: String, val subject_ids: List<Int>)
}
