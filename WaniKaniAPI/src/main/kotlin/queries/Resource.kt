package queries

enum class Resource {

    Resets {
        override fun getBuilder(): QueryBuilderBase = ResetsQueryBuilder()
    },
    SpacedRepetitionSystem {
        override fun getBuilder(): QueryBuilderBase = SpacedRepetitionSystemQueryBuilder()
    },
    StudyMaterial {
        override fun getBuilder(): QueryBuilderBase = StudyMaterialQueryBuilder()
    };

    abstract fun getBuilder(): QueryBuilderBase
}