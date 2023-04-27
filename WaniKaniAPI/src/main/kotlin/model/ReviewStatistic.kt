package model

import kotlinx.serialization.Serializable

// Note by API
// Percentage correct can be calculated by rounding the result of ((meaning_correct + reading_correct) / (meaning_correct + reading_correct + meaning_incorrect + reading_incorrect)) * 100
@Serializable
data class ReviewStatistic(
    val created_at: String,
    val hidden: Boolean,
    val meaning_correct: Int,
    val meaning_current_streak: Int,
    val meaning_incorrect: Int,
    val meaning_max_streak: Int,
    val percentage_correct: Int,
    val reading_correct: Int,
    val reading_current_streak: Int,
    val reading_incorrect: Int,
    val reading_max_streak: Int,
    val subject_id: Int,
    val subject_type: String
)
