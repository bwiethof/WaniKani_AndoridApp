package models

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val current_vacation_started_at: String,
    val id: String,
    val level: Int,
    val preferences: Preferences,
    val profile_url: String,
    val started_at: String,
    val subscription: Subscription,
    val username: String
) {
    @Serializable
    data class Subscription(
        val active: Boolean,
        val max_level_granted: Int,
        val period_ends_at: String?,
        val type: String,
    )

    @Serializable
    data class Preferences(
        val default_voice_actor_id: Int,
        val lessons_autoplay_audio: Boolean,
        val lessons_batch_size: Int,
        val lessons_presentation_order: String,
        val reviews_autoplay_audio: Boolean,
        val reviews_display_srs_indicator: Boolean
    )
}
