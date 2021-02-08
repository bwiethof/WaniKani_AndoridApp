package wanikaniAPI.API

import wanikaniAPI.*
import com.google.gson.*
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import kotlinx.coroutines.runBlocking
import wanikaniAPI.filter.Assignments


class WaniKaniService {
    private lateinit var client: HttpClient
    private lateinit var token: String
    private lateinit var gson: Gson



    fun init(token: String){
        this.token = token
        client = HttpClient {
            install(JsonFeature) {
                serializer = GsonSerializer {
                    setPrettyPrinting()
                    disableHtmlEscaping()
                }
            }
        }
        val builder = GsonBuilder()
        builder.registerTypeAdapter(CollectionMeta::class.java, MetaDeserializer<CollectionMeta>())
        builder.registerTypeAdapter(RessourceMeta::class.java, MetaDeserializer<RessourceMeta>())
        gson = builder.create()
    }
    /*
    fun <T> getSubjects(): Collection<Subject<T>> {

    }
    private  fun getRadicals(ids: Array<Int>) :Collection<Subject<Radical>> {

    }
    */

    fun getAssignments(content: Assignments.AssignmentContent, pageUrl: String = ""): Collection<Assignment> {
        var url = if(pageUrl.isEmpty()) {
            Querys.base + Querys.Assignments.base + "?" + when (content) {
                Assignments.AssignmentContent.LESSON -> Querys.Assignments.forLesson
                Assignments.AssignmentContent.REVIEW -> Querys.Assignments.forReviews
                else -> ""
            }
        } else {
            pageUrl
        }

        val request = RequestData(url)
        if(request == null){
            return Collection(null,null)
        }

        val meta = gson.fromJson(request,CollectionMeta::class.java)
        val data = request.get("data").asJsonArray
        var assignments= arrayOfNulls<Ressource<Assignment>>(meta.totCoun)
        for(i in 0 until meta.totCoun) {
            try {
                val assignementMeta = gson.fromJson(data.get(i),RessourceMeta::class.java)
                assignments.set(i, Ressource(assignementMeta,getObject(data.get(i).asJsonObject)))
            } catch (e: Exception) {
                println("Error at deserialization of assignments. Message: " + e.message)
            }
        }
        return Collection(meta,assignments)
    }

    private inline fun <reified T> getObject(data: JsonObject): T {
        val obj = data.get("data")
        return Gson().fromJson(obj,T::class.java)
    }

    private fun RequestData(query: String): JsonObject? {
        val request = runBlocking {
            try {
                client.get<JsonObject>(query) {
                    headers {
                        append("Wanikani-Revision", "20170710")
                        append("Authorization", "Bearer %s".format(token))
                    }
                }
            } catch (e: ClientRequestException) {
                println("Excpetion on client side. " + e.message)
                null
            }

        }
        return request
    }

}




