package queries.filters

enum class ParameterType {
    Nothing {
        override fun getCondition(): FilterParameter = NothingFilterParameter()
    },
    Hidden {
        override fun getCondition(): FilterParameter = HiddenFilterParameter()
    },
    Ids {
        override fun getCondition(): FilterParameter = IdsFilterParameter()
    };

    abstract fun getCondition(): FilterParameter
}

