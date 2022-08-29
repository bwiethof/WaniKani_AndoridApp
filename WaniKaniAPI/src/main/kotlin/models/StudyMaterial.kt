package models

import kotlinx.serialization.Serializable

@Serializable
data class StudyMaterial(
    val created_at: String,
    val hidden: Boolean,
    val meaning_note: String?,
    val meaning_synonyms: List<String>,
    val reading_note: String?,
    val subject_id: Int,
    val subject_type: String
)

