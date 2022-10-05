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