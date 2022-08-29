package models

import kotlinx.serialization.Serializable

@Serializable
data class Review(
    val assignment_id: Int,
    val created_at: String,
    val ending_srs_stage: Int,
    val incorrect_meaning_answers: Int,
    val incorrect_reading_answers: Int,
    val spaced_repetition_system_id: Int,
    val starting_srs_stage: Int,
    val subject_id: Int
)
