package models

import kotlinx.serialization.Serializable

@Serializable
data class Vocabulary(
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
    val component_subject_ids: List<Int>,
    val context_sentences: List<Context>,
    val parts_of_speech: List<String>,
    val pronunciation_audios: List<PronunciationAudio>,
    val reading_mnemonic: String,
    val readings: List<Reading>
) : Subject() {
    @Serializable
    data class Reading(
        val accepted_answer: Boolean, val primary: Boolean, val reading: String
    )

    @Serializable
    data class Context(val en: String, val ja: String)

    @Serializable
    data class PronunciationAudio(
        val content_type: String, val metadata: Metadata, val url: String
    ) {
        @Serializable
        data class Metadata(
            val gender: String,
            val pronunciation: String,
            val source_id: Int,
            val voice_actor_id: Int,
            val voice_actor_name: Int,
            val voice_description: String
        )
    }
}