package models

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject


@Serializable
data class Radical(
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
    val character_images: List<Image>?
) : Subject() {

    object ImageSerializer :
        JsonContentPolymorphicSerializer<ImageType>(ImageType::class) {
        override fun selectDeserializer(element: JsonElement) =
            when {
                "inline_styles" in element.jsonObject -> SVG.serializer()
                else -> PNG.serializer()
            }
    }

    @Serializable(with = ImageSerializer::class)
    abstract class ImageType


    @Serializable
    data class Image(
        val url: String, val content_type: String, val metadata: ImageType
    )

    @Serializable
    data class SVG(val inline_styles: Boolean) : ImageType()

    @Serializable
    data class PNG(val color: String, val dimensions: String, val style_name: String) : ImageType()
}


