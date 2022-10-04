package queries.filters

class ParameterComposition(private val allowedParameter: List<ParameterType> = emptyList()) {
    private val conditions: MutableList<FilterParameter> = mutableListOf()

    infix fun ParameterType.eq(value: Any?) {
        addCondition(this.getCondition(), value)
    }

    private fun addCondition(condition: FilterParameter, value: Any?) {
        if (!allowedParameter.contains(condition.parameterType))
            throw IllegalArgumentException("Parameter ${condition.parameterType} not allowed. ${if (allowedParameter.isEmpty()) "No Parameter accepted" else "Only ${allowedParameter.joinToString()} allowed"}")
        condition.setValue(value)
        conditions.add(condition)
    }

    fun build(): Map<String, String> {
        return conditions.mapNotNull {
            it.getParameterPair()
        }.toMap()
    }
}