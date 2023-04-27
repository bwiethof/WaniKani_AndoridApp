package model

import kotlinx.serialization.Serializable

@Serializable
data class Assignment(
    val available_at: String?,
    val burned_at: String?,
    val created_at: String,
    val hidden: Boolean,
    val passed_at: String?,
    val resurrected_at: String?,
    val srs_stage: Int,
    val started_at: String?,
    val subject_id: Int,
    val subject_type: String,
    val unlocked_at: String?
)


