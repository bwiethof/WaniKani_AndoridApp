package wanikaniAPI.API
//
// Object(-class) to setup Querys
// TODO: discuss usage of companion objects for different Resource types for readability
object Querys {

    const val base = "https://api.wanikani.com/v2/"
    const val user = "user"
    object Assignments {
        const val base = "assignments"
        const val forLesson = "immediately_available_for_lessons"
        const val forReviews = "immediately_available_for_review=true"
        const val updatedAfter = "updated_after"
    }

    object Subjects {
        const val base = "subjects"
        const val idSelection = "ids=" // list of subject ids
        const val typeSelection = "types=" // string list of types
        const val levelSelection = "levels="  // int list of levels (max=60)
        const val updatedFilter = "updated_after" // only subjects after input
    }


}