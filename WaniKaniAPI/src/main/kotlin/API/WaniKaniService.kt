package wanikaniAPI.API

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.gson.*
import kotlinx.coroutines.runBlocking
import wanikaniAPI.Assignment
import wanikaniAPI.filter.Assignments


class WaniKaniService {
    private lateinit var client: HttpClient
    private lateinit var token: String
    private lateinit var _gson: Gson


    fun init(token: String) {
        this.token = token
        client = HttpClient {
            install(ContentNegotiation) {
                gson()
            }
        }
        val builder = GsonBuilder()
        builder.registerTypeAdapter(CollectionMeta::class.java, MetaDeserializer<CollectionMeta>())
        builder.registerTypeAdapter(ResourceMeta::class.java, MetaDeserializer<ResourceMeta>())
        _gson = builder.create()
    }
    /*
    fun <T> getSubjects(): Collection<Subject<T>> {

    }
    private  fun getRadicals(ids: Array<Int>) :Collection<Subject<Radical>> {

    }
    */

    fun getAssignments(
        content: Assignments.AssignmentContent,
        pageUrl: String = ""
    ): Collection<Assignment> {
        val url = pageUrl.ifEmpty {
            Querys.base + Querys.Assignments.base + "?" + when (content) {
                Assignments.AssignmentContent.LESSON -> Querys.Assignments.forLesson
                Assignments.AssignmentContent.REVIEW -> Querys.Assignments.forReviews
                else -> ""
            }
        }

        val request = runBlocking {
                RequestData(url)
        }
        val meta = _gson.fromJson(request, CollectionMeta::class.java)
        val data = request.get("data").asJsonArray
        val assignments = arrayOfNulls<Resource<Assignment>>(meta.totCoun)
        for (i in 0 until meta.totCoun) {
            try {
                val assignmentMeta = _gson.fromJson(data.get(i), ResourceMeta::class.java)
                assignments[i] = Resource(assignmentMeta, getObject(data.get(i).asJsonObject))
            } catch (e: Exception) {
                println("Error at deserialization of assignments. Message: " + e.message)
            }
        }
        return Collection(meta, assignments)
    }

    private inline fun <reified T> getObject(data: JsonObject): T {
        val obj = data.get("data")
        return Gson().fromJson(obj, T::class.java)
    }

    private suspend fun RequestData(query: String): JsonObject {
        return client.get(query) {
                headers {
                    append("Wanikani-Revision", "20170710")
                    append("Authorization", "Bearer %s".format(token))
                }
            }.body()
    }

}




