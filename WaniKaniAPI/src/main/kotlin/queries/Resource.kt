package queries

enum class Resource {
    Assignments {
        override fun getBuilder(): QueryBuilderBase = AssignmentsQueryBuilder()
    },
    LevelProgressions {
        override fun getBuilder(): QueryBuilderBase = LevelProgressionsQueryBuilder()
    },
    Resets {
        override fun getBuilder(): QueryBuilderBase = ResetsQueryBuilder()
    },
    Reviews {
        override fun getBuilder(): QueryBuilderBase = ReviewQueryBuilder()
    },
    ReviewsStatistics {
        override fun getBuilder(): QueryBuilderBase = ReviewStatisticsQueryBuilder()
    },
    SpacedRepetitionSystem {
        override fun getBuilder(): QueryBuilderBase = SpacedRepetitionSystemQueryBuilder()
    },
    StudyMaterials {
        override fun getBuilder(): QueryBuilderBase = StudyMaterialsQueryBuilder()
    },
    Subjects {
        override fun getBuilder(): QueryBuilderBase = SubjectsQueryBuilder()
    },
    Summary {
        override fun getBuilder(): QueryBuilderBase = SummaryQueryBuilder()
    },
    User {
        override fun getBuilder(): QueryBuilderBase = UserQueryBuilder()
    },
    VoiceActors {
        override fun getBuilder(): QueryBuilderBase = VoiceActorsQueryBuilder()
    };

    abstract fun getBuilder(): QueryBuilderBase
}

fun model.Assignment.getResource() = Resource.Assignments
fun model.LevelProgression.getResource() = Resource.LevelProgressions
fun model.Reset.getResource() = Resource.Resets
fun model.Review.getResource() = Resource.Reviews
fun model.ReviewStatistic.getResource() = Resource.ReviewsStatistics
fun model.SpacedRepetitionSystem.getResource() = Resource.SpacedRepetitionSystem
fun model.StudyMaterial.getResource() = Resource.StudyMaterials
fun model.Subject.getResource() = Resource.Subjects
fun model.Summary.getResource() = Resource.Summary
fun model.User.getResource() = Resource.User
fun model.VoiceActor.getResource() = Resource.VoiceActors