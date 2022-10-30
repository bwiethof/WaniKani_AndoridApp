package queries.filters

import models.Date
import models.Subject
import java.time.*
import java.time.format.DateTimeFormatter



interface FilterParameter {
    val queryParam: String
    val parameterType: ParameterType
    fun setValue(value: Any?)
    fun getParameterPair(): Pair<String, String>?
}

// TODO look for checked cast variant to set type
@Suppress("UNCHECKED_CAST")
abstract class FilterParameterImpl<T : Any>(
    override val queryParam: String,
    override val parameterType: ParameterType
) :
    FilterParameter {
    protected var data: T? = null
    override fun setValue(value: Any?) {
        this.data = value as T?
    }
}

class IntFilterParameterImpl(queryParam: String, type: ParameterType) :
    FilterParameterImpl<Int>(queryParam, type) {
    override fun getParameterPair(): Pair<String, String>? {
        return data?.let { Pair(queryParam, it.toString()) }
    }
}

class IntListFilterParameterImpl(queryParam: String, type: ParameterType) :
    FilterParameterImpl<List<Int>>(queryParam, type) {
    override fun getParameterPair(): Pair<String, String>? {
        return data?.let { Pair(queryParam, it.joinToString(",")) }
    }
}

class BooleanFilterParameterImpl(queryParam: String, type: ParameterType) :
    FilterParameterImpl<Boolean>(queryParam, type) {
    override fun getParameterPair(): Pair<String, String>? {
        return data?.let { Pair(queryParam, it.toString()) }
    }
}

class StringFilterParameterImpl(queryParam: String, type: ParameterType) :
    FilterParameterImpl<String>(queryParam, type) {
    override fun getParameterPair(): Pair<String, String>? {
        return data?.let { queryParam to it }//Pair(queryParam, it) }
    }
}

class StringListFilterParameterImpl(queryParam: String, type: ParameterType) :
    FilterParameterImpl<List<String>>(queryParam, type) {
    override fun getParameterPair(): Pair<String, String>? {
        return data?.let { Pair(queryParam, it.joinToString(",")) }
    }
}

class DateFilterParameterImpl(queryParam: String, type: ParameterType) :
    FilterParameterImpl<Date>(queryParam, type) {
    override fun setValue(value: Any?) {
        DateTimeFormatter.ISO_INSTANT.parse(value as String)
        super.setValue(value)
    }

    override fun getParameterPair(): Pair<String, String>? {
        return data?.let { Pair(queryParam, it) }
    }
}

class SubjectTypeListFilterParameterImpl(queryParam: String, type: ParameterType) :
    FilterParameterImpl<List<Subject.Type>>(queryParam, type) {
    override fun getParameterPair(): Pair<String, String>? {
        return data?.let { types ->
            Pair(queryParam, types.joinToString { it.toString() })
        }
    }
}

class NothingFilterParameterImpl(queryParam: String, type: ParameterType) :
    FilterParameterImpl<Nothing>(queryParam, type) {
    override fun setValue(value: Any?) {
        // Nothing needs to be set
    }

    override fun getParameterPair(): Pair<String, String> {
        return Pair(queryParam, "")
    }
}




