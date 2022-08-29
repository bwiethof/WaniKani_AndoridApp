package models

import kotlinx.serialization.Serializable

@Serializable
data class Kanji(
    override val auxiliary_meanings: List<AuxiliaryMeaning>?,
    override val characters: String?,
    override val created_at: String,
    override val document_url: String,
    override val hidden_at: String?,
    override val lesson_position: Int,
    override val level: Int,
    override val meaning_mnemonic: String,
    override val meanings: List<Meaning>,
    override val slug: String,
    override val spaced_repetition_system_id: Int,
    val amalgamation_subject_ids: List<Int>,
    val component_subject_ids: List<Int>,
    val meaning_hint: String?,
    val reading_hint: String?,
    val reading_mnemonic: String,
    val readings: List<Reading>,
    val visually_similar_subject_ids: List<Int>
) : Subject() {
    @Serializable
    data class Reading(
        val reading: String,
        val primary: Boolean,
        val accepted_answer: Boolean,
        val type: String
    )

}