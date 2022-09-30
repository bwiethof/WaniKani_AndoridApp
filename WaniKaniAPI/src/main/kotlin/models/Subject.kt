package models

import kotlinx.serialization.Serializable


@Serializable
abstract class Subject {
    abstract val auxiliary_meanings: List<AuxiliaryMeaning>?

    abstract val created_at: String

    abstract val document_url: String

    abstract val hidden_at: String?

    abstract val lesson_position: Int

    abstract val spaced_repetition_system_id: Int

    abstract val meaning_mnemonic: String

    abstract val level: Int

    abstract val meanings: List<Meaning>

    abstract val slug: String

    abstract val characters: String?


    @Serializable
    data class Meaning(val meaning: String, val primary: Boolean, val accepted_answer: Boolean)

    @Serializable
    data class AuxiliaryMeaning(val meaning: String, val type: String)

    enum class Type {
        RADICAL {
            override fun toString(): String {
                return super.toString().lowercase()
            }
        },
        REVIEW {
            override fun toString(): String {
                return super.toString().lowercase()
            }
        },
        LESSON {
            override fun toString(): String {
                return super.toString().lowercase()
            }
        }
    }

}



