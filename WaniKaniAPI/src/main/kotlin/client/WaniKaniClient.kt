package client

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.get
import io.ktor.http.appendPathSegments
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import model.Assignment
import model.Collection
import model.User
import queries.QueryBuilder
import queries.QueryBuilderBase

private const val apiRevisionKey = "Wanikani-Revision"


class WaniKaniClient(
    var token: String,
    val waniKaniApiEndpoint: String = "https://api.wanikani.com/v2/",
    val apiRevision: String = "20170710"
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

    fun setNewToken(newToken: String) {
        token = newToken
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

    suspend fun getAssignments(initializer: QueryBuilder.() -> Unit): model.simple.Collection<Assignment> =
        model.simple.Collection.fromWanikani(
            getCollection(
                getBuilder(
                    queries.Resource.Assignments,
                    initializer
                )
            )
        )

    suspend fun getUser(initializer: QueryBuilder.() -> Unit = {}): model.simple.Report<User> =
        model.simple.Report.fromWanikani(
            get(
                getBuilder(
                    queries.Resource.User,
                    initializer
                )
            )
        )


    suspend fun getReviews(initializer: QueryBuilder.() -> Unit): model.simple.Collection<Assignment> =
        model.simple.Collection.fromWanikani(
            getCollection(
                getBuilder(
                    queries.Resource.Reviews,
                    initializer
                )
            )
        )

    private suspend inline fun <reified T> getCollection(builder: QueryBuilderBase): Collection<T> {
        if (!isCollectionQuery(builder)) throw IllegalArgumentException("Collections request does not allow specific Id")
        return get(builder)
    }


    private fun isCollectionQuery(queryBuilder: QueryBuilderBase) =
        queryBuilder.specificResourceId == null

    private fun getBuilder(resource: queries.Resource, initializer: QueryBuilder.() -> Unit) =
        QueryBuilder().apply { from(resource) }.apply(initializer).build()
            ?: throw IllegalArgumentException("Resource type needs to be specified!")


}
