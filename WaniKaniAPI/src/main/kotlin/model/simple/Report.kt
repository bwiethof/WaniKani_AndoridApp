package model.simple

typealias WanikaniReport<T> = model.Report<T>

data class Report<T>(val metadata: MetaData, val data: T) {

    data class MetaData(
        val dataUpdatedAt: String,
        val url: String,
        val obj: String,
    )

    companion object {
        fun <T> fromWanikani(report: WanikaniReport<T>): Report<T> = with(report) {
            Report(MetaData(data_updated_at, url, obj), data)
        }

        fun <T> toWanikani(report: Resource<T>): WanikaniReport<T> = with(report) {
            WanikaniReport(metaData.dataUpdatedAt, metaData.url, metaData.obj, data)
        }
    }
}