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
import model.Report
import model.Resource
import model.Review
import queries.QueryBuilder
import queries.QueryBuilderBase
import kotlin.reflect.KClass

private const val apiRevisionKey = "Wanikani-Revision"


class WaniKaniClient(
    token: String,
    waniKaniApiEndpoint: String = "https://api.wanikani.com/v2/",
    apiRevision: String = "20170710"
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

    suspend fun getAssignments(initializer: QueryBuilder.() -> Unit): model.simple.Collection<Assignment> =
        model.simple.Collection.fromWanikani(
            getCollection(
                getBuilder(
                    queries.Resource.Assignments,
                    initializer
                )
            )
        )


    suspend fun getReviews(initializer: QueryBuilder.() -> Unit): Collection<Review> {
        return getCollection(getBuilder(queries.Resource.Reviews, initializer))
    }

    private suspend inline fun <reified T> getCollection(builder: QueryBuilderBase): Collection<T> {

        if (!isCollectionQuery(builder)) throw IllegalArgumentException("Collections request does not allow specific Id")
        return get(builder)
    }


    private fun isCollectionQuery(queryBuilder: QueryBuilderBase) =
        queryBuilder.specificResourceId == null

    private fun getBuilder(resource: queries.Resource, initializer: QueryBuilder.() -> Unit) =
        QueryBuilder().apply { from(resource) }.apply(initializer).build()
            ?: throw IllegalArgumentException("Resource type needs to be specified!")


    // Keeping existing fun keep existing functions
    private fun getBuilder(initializer: QueryBuilder.() -> Unit) =
        QueryBuilder().apply(initializer).build()
            ?: throw IllegalArgumentException("Resource type needs to be specified!")


    // TODO make interfaces more generic or simplify queries


    private suspend fun <T : Any> getCollection(
        initializer: QueryBuilder.() -> Unit, c: KClass<T>
    ): Collection<T> {
        val builder = getBuilder(initializer)

        if (!isCollectionQuery(builder)) throw IllegalArgumentException("Collections request does not allow specific Id")

        return get(builder)
    }

    private suspend fun <T> getResource(initializer: QueryBuilder.() -> Unit): Resource<T> {
        val builder = getBuilder(initializer)

        if (isCollectionQuery(builder)) throw IllegalArgumentException("Specific resource requires id specification")

        return get(builder)
    }


    private suspend fun <T> getReport(initializer: QueryBuilder.() -> Unit): Report<T> {
        val builder = getBuilder(initializer)

        return get(builder)
    }

    // Marking this as private since it doesn't work
    // get ist a reified fun -> function call needs to be either directly or from a reified function call
    // Possible solutions:
    // make "get" public
    private suspend fun <T> getSimpleResource(initializer: QueryBuilder.() -> Unit): model.simple.Resource<T> =
        model.simple.Resource.fromWanikani(getResource(initializer))

//    private suspend fun <T : Any> getSimpleCollection(initializer: QueryBuilder.() -> Unit): model.simple.Collection<T> =
//        model.simple.Collection.fromWanikani(getCollection(initializer))

    private suspend fun <T> getSimpleReport(initializer: QueryBuilder.() -> Unit): model.simple.Report<T> =
        model.simple.Report.fromWanikani(getReport(initializer))
}