import API.ApiService
import client.WaniKaniClient
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.runBlocking
import model.Assignment
import queries.Resource
import queries.filters.FilterParameter
import queries.filters.FilterParameterImpl
import queries.filters.ParameterType
import queries.query

@OptIn(DelicateCoroutinesApi::class)
fun main(args: Array<String>) = runBlocking {
    println("Hello World!")
    val token = "a7543476-9981-49c7-905b-3c316acee7f7"

    val api: ApiService by lazy {
        ApiService.create(token)
    }


    val client = WaniKaniClient(token)

/*
    val assignments = client.getSimpleCollection<Assignment> {
        from(Resource.Assignments)
        where {
            ParameterType.Hidden eq true
        }
    }*/


    /*    val service = WaniKaniService()
        val token = "a7543476-9981-49c7-905b-3c316acee7f7"
        service.init(token)*/

/*    class StringImpl: FilterParameterImpl<String>("", ParameterType.Hidden){
        override fun getParameterPair(): Pair<String, String>? {
            TODO("Not yet implemented")
        }
    }

    class BoolImpl: FilterParameterImpl<Boolean>("", ParameterType.Hidden){
        override fun getParameterPair(): Pair<String, String>? {
            TODO("Not yet implemented")
        }
    }

    val testString = StringImpl()
    val trestBool = testString as BoolImpl*/

    val assignmentParameters = query {
        from(Resource.Assignments)
        where {
            ParameterType.Unlocked eq "true"
            ParameterType.SubjectIds eq listOf(1, 2, 3)
        }
    }.build()

    println(assignmentParameters)

    val singleAssignment = query {
        from(Resource.Assignments)
        matches(4)
    }
}


//Token a7543476-9981-49c7-905b-3c316acee7f7

//subject_type = "radical"
//srs_stage = 0
//unlocked_at = "2021-01-27T13:24:41.079227Z"
//started_at = null
//passed_at = null
//burned_at = null
//available_at = null
//resurrected_at = null