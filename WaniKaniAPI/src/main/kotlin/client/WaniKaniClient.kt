package client

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import model.Collection
import model.Report
import model.Resource
import queries.QueryBuilder
import queries.QueryBuilderBase

private const val apiRevisionKey = "Wanikani-Revision"



class Resolver{



}

class WaniKaniClient(
    token: String, waniKaniApiEndpoint: String = "https://api.wanikani.com/v2/", apiRevision: String = "20170710"
) {

    private val client: HttpClient

    init {
        client = HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                    prettyPrint = true
                })
            }
            install(Auth) {
                bearer {
                    loadTokens {
                        BearerTokens(token, "")
                    }
                }
            }
            expectSuccess = true
            defaultRequest {
                url(waniKaniApiEndpoint)
                headers.append(apiRevisionKey, apiRevision)
            }
        }
    }


    private suspend inline fun <reified T> get(builder: QueryBuilderBase): T {
        val response = client.get {
            url {
                appendPathSegments(builder.route)
                encodedParameters.apply { }
                builder.filterParameterMap?.forEach { (paramKey, paramValue) ->
                    encodedParameters.append(
                        paramKey, paramValue
                    )
                }
            }
        }

        return response.body()
    }

    private fun isCollectionQuery(queryBuilder: QueryBuilderBase) = queryBuilder.specificResourceId == null

    private fun getBuilder(initializer: QueryBuilder.() -> Unit) = QueryBuilder().apply(initializer).build()
        ?: throw IllegalArgumentException("Resource type needs to be specified!")


    private suspend fun <T> getCollection(initializer: QueryBuilder.() -> Unit): Collection<T> {
        val builder = getBuilder(initializer)

        if (!isCollectionQuery(builder)) throw IllegalArgumentException("Collections request does not allow specific Id")

        return get(builder)
    }

    private suspend fun <T> getResource(initializer: QueryBuilder.() -> Unit): Resource<T> {
        val builder = getBuilder(initializer)

        if (isCollectionQuery(builder)) throw IllegalArgumentException("Specific resource requires id specification")

        return get(builder)
    }
//
//    private suspend fun <T: queries.Resource> testFun(resource: queries.Resource,initializer: QueryBuilder.() -> Unit): Report<T> {
//        val builder = QueryBuilder().apply{
//            from(resource)
//        }.apply(initializer)
//
//
//
//
//    }

    private suspend fun <T> getReport(initializer: QueryBuilder.() -> Unit): Report<T> {
        val builder = getBuilder(initializer)

        return get(builder)
    }

    suspend fun <T> getSimpleResource(initializer: QueryBuilder.() -> Unit): model.simple.Resource<T> =
        model.simple.Resource.fromWanikani(getResource(initializer))

    suspend fun <T> getSimpleCollection(initializer: QueryBuilder.() -> Unit): model.simple.Collection<T> =
        model.simple.Collection.fromWanikani(getCollection(initializer))

    suspend fun <T> getSimpleReport(initializer: QueryBuilder.() -> Unit): model.simple.Report<T> =
        model.simple.Report.fromWanikani(getReport(initializer))
}