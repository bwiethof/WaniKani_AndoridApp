import queries.Resource
import queries.filters.ParameterType
import queries.query

fun main(args: Array<String>) {
    println("Hello World!")

/*    val service = WaniKaniService()
    val token = "a7543476-9981-49c7-905b-3c316acee7f7"
    service.init(token)*/

    val assignmentParameters = query {
        from(Resource.Assignments)
        where {
            ParameterType.Unlocked eq true
            ParameterType.SubjectIds eq listOf(1, 2, 3)
        }
    }.build()

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