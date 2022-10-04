package queries.filters

interface FilterParameter {
    val queryParam: String
    val parameterType: ParameterType
    fun setValue(value: Any?)
    fun getParameterPair(): Pair<String, String>?
}

// TODO look for checked cast variant to set type
@Suppress("UNCHECKED_CAST")
abstract class FilterParameterImpl<T : Any>(
    override val parameterType: ParameterType
) :
    FilterParameter {
    protected var data: T? = null
    override fun setValue(value: Any?) {
        this.data = value as T?
    }
}



