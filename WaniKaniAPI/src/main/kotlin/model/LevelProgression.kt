package model

import kotlinx.serialization.Serializable

@Serializable
data class LevelProgression(
    val abandoned_at: String?,
    val completed_at: String?,
    val created_at: String?,
    val level: Int,
    val passed_at: String,
    val started_at: String?,
    val unlocked_at: String?
)

