package wanikaniAPI.API

import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import wanikaniAPI.Radical
import java.lang.reflect.Type

@Suppress("UNCHECKED_CAST")
class MetaDeserializer<T>: JsonDeserializer<T> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): T {
        val obj = json?.asJsonObject ?: throw  Exception("JsonElement was null.")
        return when(typeOfT) {
            CollectionMeta::class.java -> {
                val pages = Gson().fromJson(obj.get(CollectionMeta.querys[2]), CollectionMeta.Page::class.java)
                CollectionMeta(obj.get(CollectionMeta.querys[0]).asString,obj.get(CollectionMeta.querys[1]).asString,pages,obj.get(
                    CollectionMeta.querys[3]).asInt,obj.get(CollectionMeta.querys[4]).asString) as T
            }
            else -> {
                ResourceMeta(
                    obj.get(ResourceMeta.querys[0]).asString,
                    obj.get(ResourceMeta.querys[1]).asString,
                    obj.get(
                        ResourceMeta.querys[2]
                    ).asString,
                    obj.get(ResourceMeta.querys[3]).asString
                ) as T
            }
        }
    }

}

class RadicalDeserializer: JsonDeserializer<Radical>{
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Radical {
        //
        // Deserialize data with default properties
        val radical: Radical = Gson().fromJson(json,Radical::class.java)
        val prevData: Array<Radical.Image> = radical.images!!
        //
        // Get image specific data and allocte memory
        val imageData = json!!.asJsonObject.get("character_images").asJsonArray
        val images = ArrayList<Radical.Image>(0)
        //
        // Loop over all image objects
        for(i in 0 until imageData.size()) {
            if(imageData[i].asJsonObject.get("content_type").asString == RadicalImageTypes.png) {
                val metaData =  Gson().fromJson(imageData[i].asJsonObject.get("metadata"), Radical.PNG::class.java)
                images.add(Radical.Image(prevData.get(i).url,prevData[i].contentType,Radical.TypeSpecific(pngData = metaData, svgData = null)))
            } else if(imageData[i].asJsonObject.get("content_type").asString == RadicalImageTypes.svg) {
                val metaData =  Gson().fromJson(imageData[i].asJsonObject.get("metadata"), Radical.SVG::class.java)
                images.add(Radical.Image(prevData.get(i).url,prevData[i].contentType,Radical.TypeSpecific(pngData = null, svgData = metaData)))
            } else{
                println("Image type not found. Type = " + imageData[i].asJsonObject.get("content_type").asString)
            }
        }
        //
        // return merged radical object
        return Radical(radical.alamagationIds,radical.characters,images.toTypedArray())
    }

}