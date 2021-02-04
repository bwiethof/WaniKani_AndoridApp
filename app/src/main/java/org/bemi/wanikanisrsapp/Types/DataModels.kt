package Types

import com.google.gson.annotations.SerializedName

/////////////////////
// Meta data classes
data class CollectionMeta(val obj: String, val url: String, val page: Page, val totCoun: Int, val dateUpd: String?) {
    companion object{ val querys = arrayOf("object","url","pages","total_count","data_updated_at") }
    data class Page(val per_page: String, val next_url: String?, val previous_url: String?)
}
//
// Meta Data in every Ressource Object returned from API
data class RessourceMeta(val id : String?, val obj: String?, val url: String?, val dateUpd: String?) {
    companion object{ val querys = arrayOf("id","object","url","data_updated_at") }
}
////////////////////////////////////
// Basic generic response types
//
// Ressource containing common meta Data and Ressource specific Data
data class Ressource<T>(val meta: RessourceMeta, val data: T)
//
// Collection containing Collection meta Data and a Array of Ressources deifned as above
data class Collection<T>(val meta: CollectionMeta?,val data: Array<Ressource<T>?>?)
///////////////////////////////////
// Objects Models based on WaniKani API
//
// Assignments represent progress of specific subjects as lessons and reviews
data class Assignment(val subject_id: Int, val subject_type: String, val srs_stage: Int, val unlocked_at: String?, val started_at: String?, val passed_at: String?, val burned_at: String?, val available_at: String?, val resurrected_at: String?)
//
// User specific Data
data class User(val id: String, val username: String, val level: Int, val profile_url: String, val started_at: String, val current_vacation_started_at: String, val subscription: Subscription, val preferences: Prefereneces) {
    data class Subscription(val active: Boolean, val type: String, val maxLevel: Int, val periodEnd: String)
    data class Prefereneces(val voiceId: Int, val lessonAutoplay: Boolean, val lessonSize: Int, val lessonOrder: String, val reviewAutoplay: Boolean, val rewievIndicator: Boolean)
}
/////////////////////////////////////
//Subject Types
//
// Subject divided ans common and specific Data
data class Subject<T>(val subjectData: SubjectData,val specificData: T)
//
// Common Data in every Subject
data class SubjectData(val characters: String, @SerializedName("auxiliary_name") val auxMeanings: Array<AuxMeaning>, val meanings: Array<Meaning>) {
    data class Meaning(val meaning: String, val primary:  Boolean, @SerializedName("accepted_answer") val acceptedAnswer: Boolean)
    data class AuxMeaning(val meaning: String, val type: String)
}
//
// Specific Data for Radicals, generic for image type(svg/png)
data class Radical<T: Radical.ImageMeta>(@SerializedName("amalgamation_subject_ids") val alamagationIds:Array<Int>, val characters: String?,@SerializedName("character_images") val images: Array< Image<T> >) {
    interface ImageMeta
    data class Image<T: ImageMeta>(val url: String, @SerializedName("content_type") val contentType: String, val metadata: T)
    data class SVG(@SerializedName("inline_styles") val styles: Boolean): ImageMeta
    data class PNG(val color: String, val dimensions: String, @SerializedName("sytle_name") val styleName: String) :ImageMeta
}
//
// Specific Data for Kanjis
data class Kanji(@SerializedName("amalgamation_subject_ids") val alamagationIds:Array<Int>, @SerializedName("component_subject_ids") val componentIds: Array<Int>, @SerializedName("meaning_hint") val meaningHint: String?, @SerializedName("reading_hint") val readingHint: String?, @SerializedName("reading_mnemonic") val readingMnemonic: String, val readings: Array<Reading>, @SerializedName("visually_similar_subject_ids") val visuallySimilarIds: Array<Int>) {
    data class Reading(val reading: String, val primary: Boolean, @SerializedName("accepted_answer") val acceptedAnswer: Boolean, val type: String)
}
//
// Specific Data for Vocabularies
data class Vocabulary(@SerializedName("component_subject_ids") val componentIds: Array<Int>, @SerializedName("context_sentences") val contextSentences: Array<Context>, @SerializedName("meaning_mnemonic") val meaningMnemonic: String, @SerializedName("parts_of_speech") val partsOfSpeech: Array<String>, @SerializedName("pronunciation_audios") val pronunciationAudios: Array<Pronunciation>, val readings: Array<Reading>, @SerializedName("reading_mnemonic") val readingMnemonic: String){
    data class Reading(val reading: String, val primary: Boolean, @SerializedName("accepted_answer") val acceptedAnswer: Boolean, val type: String)
    data class Context(val en: String, val ja: String)
    data class Pronunciation(val url: String, @SerializedName("content_type") val contentType: String, val meta: Meta) {
        data class Meta(val gender: String, @SerializedName("source_id")  val sourceId: Int, val pronunciation: String, @SerializedName("voice_actor_id") val voiceActorId: Int, @SerializedName("voice_actor_name") val voiceActorName: Int, @SerializedName("voice_description") val voiceDescription: String)
    }
}
//
// Reviews log correct and incorrect answering of an assignment
data class Review(@SerializedName("assignment_id") val assignmentId: Int, @SerializedName("created_at") val createdAt: String, @SerializedName("ending_srs_stage") val endingSrsStage: Int, @SerializedName("incorrect_meaning_answers") val incorrectMeaningAnswers: Int, @SerializedName("incorrect_reading_answers") val incorrectReadingAnswers: Int, @SerializedName("spaced_repetition_system_id") val spacedRepetitionSystemId: Int, @SerializedName("starting_srs_stage") val startingSrsStage: Int, @SerializedName("subject_id") val subjectId: Int)
//
// Summarys contains a report about available lessons/reviwes from now until 24h grouped by hour
data class Summary(val lessons: Array<FutureSubject>, val nextReviwesAt: String?, val review: Array<FutureSubject>) {
    data class FutureSubject(val availableAt: String, val ids: Array<Int>)
}