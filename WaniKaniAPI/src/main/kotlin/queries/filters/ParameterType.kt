package queries.filters


enum class ParameterType {
    AssignmentIds {
        override fun getCondition(): FilterParameter =
            IntListFilterParameterImpl(assignmentIdsParam, this)
    },
    AvailableAfter {
        override fun getCondition(): FilterParameter =
            DateFilterParameterImpl(availableAfterParam, this)
    },
    AvailableBefore {
        override fun getCondition(): FilterParameter =
            DateFilterParameterImpl(availableBeforeParam, this)
    },
    Burned {
        override fun getCondition(): FilterParameter = BooleanFilterParameterImpl(burnedParam, this)
    },
    Hidden {
        override fun getCondition(): FilterParameter = BooleanFilterParameterImpl(hiddenParam, this)
    },
    Ids {
        override fun getCondition(): FilterParameter = IntListFilterParameterImpl(idsParam, this)
    },
    ImmediatelyAvailableForLessons {
        override fun getCondition(): FilterParameter = NothingFilterParameterImpl(
            immediatelyAvailableForLessonsParam, this
        )
    },
    ImmediatelyAvailableForReview {
        override fun getCondition(): FilterParameter = NothingFilterParameterImpl(
            immediatelyAvailableForReviewParam, this
        )
    },
    InReview {
        override fun getCondition(): FilterParameter =
            NothingFilterParameterImpl(inReviewParam, this)
    },
    Levels {
        override fun getCondition(): FilterParameter = IntListFilterParameterImpl(levelsParam, this)
    },
    PercentagesLessThan {
        override fun getCondition(): FilterParameter =
            IntFilterParameterImpl(percentagesLessThan, this)
    },
    PercentagesGreaterThan {
        override fun getCondition(): FilterParameter =
            IntFilterParameterImpl(percentagesGreaterThan, this)
    },
    Slugs {
        override fun getCondition(): FilterParameter =
            StringListFilterParameterImpl(slugsParam, this)
    },
    SrsStages {
        override fun getCondition(): FilterParameter =
            IntListFilterParameterImpl(srsStagesParam, this)
    },
    Started {
        override fun getCondition(): FilterParameter =
            BooleanFilterParameterImpl(startedParam, this)
    },
    SubjectIds {
        override fun getCondition(): FilterParameter =
            IntListFilterParameterImpl(subjectIdsParam, this)
    },
    SubjectTypes {
        override fun getCondition(): FilterParameter = SubjectTypeListFilterParameterImpl(
            subjectTypesParam, this
        )
    },
    Types {
        override fun getCondition(): FilterParameter =
            SubjectTypeListFilterParameterImpl(typesParam, this)
    },
    Unlocked {
        override fun getCondition(): FilterParameter =
            BooleanFilterParameterImpl(unlockedParam, this)
    },
    UpdatedAfter {
        override fun getCondition(): FilterParameter =
            DateFilterParameterImpl(updatedAfterParam, this)
    };

    abstract fun getCondition(): FilterParameter
}

