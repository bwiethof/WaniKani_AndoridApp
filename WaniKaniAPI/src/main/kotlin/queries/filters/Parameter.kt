package queries.filters

import models.Date
import models.Subject
import models.isValid

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

abstract class IntFilterParameterImpl(type: ParameterType) : FilterParameterImpl<Int>(type) {
    override fun getParameterPair(): Pair<String, String>? {
        return data?.let { Pair(queryParam, it.toString()) }
    }
}

abstract class IntListFilterParameterImpl(type: ParameterType) :
    FilterParameterImpl<List<Int>>(type) {
    override fun getParameterPair(): Pair<String, String>? {
        return data?.let { Pair(queryParam, it.joinToString(",")) }
    }
}

abstract class BooleanFilterParameterImpl(type: ParameterType) :
    FilterParameterImpl<Boolean>(type) {
    override fun getParameterPair(): Pair<String, String>? {
        return data?.let { Pair(queryParam, it.toString()) }
    }
}

abstract class StringFilterParameterImpl(type: ParameterType) : FilterParameterImpl<String>(type) {
    override fun getParameterPair(): Pair<String, String>? {
        return data?.let { Pair(queryParam, it) }
    }
}

abstract class StringListFilterParameterImpl(type: ParameterType) :
    FilterParameterImpl<List<String>>(type) {
    override fun getParameterPair(): Pair<String, String>? {
        return data?.let { Pair(queryParam, it.joinToString(",")) }
    }
}

abstract class DateFilterParameterImpl(type: ParameterType) : FilterParameterImpl<Date>(type) {
    override fun setValue(value: Any?) {
        if (!(value as Date).isValid())
            throw IllegalArgumentException("Date $value does not match the expected ISO 8601 Format")
        super.setValue(value)
    }

    override fun getParameterPair(): Pair<String, String>? {
        return data?.let { Pair(queryParam, it) }
    }
}

abstract class SubjectTypeListFilterParameterImpl(type: ParameterType) :
    FilterParameterImpl<List<Subject.Type>>(type) {
    override fun getParameterPair(): Pair<String, String>? {
        return data?.let { types ->
            Pair(queryParam, types.joinToString { it.toString() })
        }
    }
}

abstract class NothingFilterParameterImpl(type: ParameterType) :
    FilterParameterImpl<Nothing>(type) {
    override fun setValue(value: Any?) {
        // Nothing needs to be set
    }

    override fun getParameterPair(): Pair<String, String>? {
        return Pair(queryParam, "")
    }
}




