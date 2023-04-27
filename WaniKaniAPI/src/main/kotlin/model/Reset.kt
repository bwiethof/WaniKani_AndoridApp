package model

import kotlinx.serialization.Serializable

@Serializable
data class Reset(
    val confirmed_at: String?,
    val created_at: String,
    val original_level: Int,
    val target_level: Int
)
