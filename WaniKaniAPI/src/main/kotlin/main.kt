import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.gson.*
import io.ktor.client.request.*
import kotlinx.coroutines.*
import wanikaniAPI.*
import wanikaniAPI.API.Querys
import wanikaniAPI.API.RadicalDeserializer
import wanikaniAPI.API.WaniKaniService


fun main(args: Array<String>) {
    println("Hello World!")

    runBlocking { TestClientFun() }

    val service = WaniKaniService()
    val token = "a7543476-9981-49c7-905b-3c316acee7f7"
    service.init(token)


}


suspend fun TestClientFun() {
    val client = HttpClient {
        install(ContentNegotiation) {
            gson()
        }
    }
    val token = "a7543476-9981-49c7-905b-3c316acee7f7"
    val query: String = Querys.base + Querys.Subjects.base
    val req =
        client.get(query) {
            headers {
                append("Wanikani-Revision", "20170710")
                append("Authorization", "Bearer %s".format(token))
            }
        }
    val testRadicalData = req.body<JsonObject>()
    val builder = GsonBuilder()
    builder.registerTypeAdapter(Radical::class.java,RadicalDeserializer())
    val bla =builder.create()
    val testObj =  bla.fromJson(testRadicalData,Radical::class.java)
    val data = req//.await()
    println(data)

}


//a7543476-9981-49c7-905b-3c316acee7f7

//subject_type = "radical"
//srs_stage = 0
//unlocked_at = "2021-01-27T13:24:41.079227Z"
//started_at = null
//passed_at = null
//burned_at = null
//available_at = null
//resurrected_at = null