package queries.filters


enum class ParameterType {
    Nothing {
        override fun getCondition(): FilterParameter = NothingFilterParameter()
    },
    Hidden {
        override fun getCondition(): FilterParameter = HiddenParameter(this)
    },
    Ids {
        override fun getCondition(): FilterParameter = IdsParameter(this)
    },
    AssignmentIds {
        override fun getCondition(): FilterParameter = AssignmentIdsParameter(this)
    },
    AvailableAfter {
        override fun getCondition(): FilterParameter = AvailableAfterParameter(this)
    },
    AvailableBefore {
        override fun getCondition(): FilterParameter = AvailableBeforeParameter(this)
    },
    Burned {
        override fun getCondition(): FilterParameter = BurnedParameter(this)
    },
    ImmediatelyAvailableForLessons {
        override fun getCondition(): FilterParameter = ImmediatelyAvailableForLessonsParameter(this)
    },
    ImmediatelyAvailableForReview {
        override fun getCondition(): FilterParameter = ImmediatelyAvailableForReviewParameter(this)
    },
    InReview {
        override fun getCondition(): FilterParameter = InReviewParameter(this)
    },
    Levels {
        override fun getCondition(): FilterParameter = LevelsParameter(this)
    },
    PercentagesLessThan {
        override fun getCondition(): FilterParameter = PercentagesLessThanParameter(this)
    },
    PercentagesGreaterThan {
        override fun getCondition(): FilterParameter = PercentagesGreaterThanParameter(this)
    },
    Slugs {
        override fun getCondition(): FilterParameter = SlugsParameter(this)
    },
    SrsStages {
        override fun getCondition(): FilterParameter = SrsStagesParameter(this)
    },
    Started {
        override fun getCondition(): FilterParameter = StartedParameter(this)
    },
    SubjectIds {
        override fun getCondition(): FilterParameter = SubjectIdsParameter(this)
    },
    SubjectTypes {
        override fun getCondition(): FilterParameter = SubjectTypesParameter(this)
    },
    Types {
        override fun getCondition(): FilterParameter = TypesParameter(this)
    },
    Unlocked {
        override fun getCondition(): FilterParameter = UnlockedParameter(this)
    },
    UpdatedAfter {
        override fun getCondition(): FilterParameter = UpdatedAfterParameter(this)
    };

    abstract fun getCondition(): FilterParameter
}

