package queries.filters


class NothingFilterParameter : FilterParameterImpl<Nothing>(ParameterType.Nothing) {

    init {
        throw IllegalArgumentException("Nothing type is not supported")
    }

    override val queryParam: String
        get() = throw Exception("Nothing cannot have a query param")

    override fun getParameterPair(): Pair<String, String>? {
        throw IllegalStateException("NothingFilterParameter cannot build a Parameter set")
    }
}

class HiddenFilterParameter : FilterParameterImpl<Boolean>(ParameterType.Hidden) {
    override var queryParam = "hidden"
    override fun getParameterPair(): Pair<String, String>? {
        return data?.let { Pair(queryParam, it.toString()) }
    }
}

class IdsFilterParameter : FilterParameterImpl<List<Int>>(ParameterType.Ids) {
    override var queryParam = "ids"
    override fun getParameterPair(): Pair<String, String>? {
        return data?.let { Pair(queryParam, it.joinToString(",")) }
    }
}