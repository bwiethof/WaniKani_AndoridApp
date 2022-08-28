package wanikaniAPI.API

/////////////////////
// Meta data classes
data class CollectionMeta(val obj: String, val url: String, val page: Page, val totCoun: Int, val dateUpd: String?) {
    companion object{ val querys = arrayOf("object","url","pages","total_count","data_updated_at") }
    data class Page(val per_page: String, val next_url: String?, val previous_url: String?)
}
//
// Meta Data in every Ressource Object returned from API
data class RessourceMeta(val id : String?, val obj: String?, val url: String?, val dateUpd: String?) {
    companion object{ val querys = arrayOf("id","object","url","data_updated_at") }
}
////////////////////////////////////
// Basic generic response types
//
// Ressource containing common meta Data and Ressource specific Data
data class Ressource<T>(val meta: RessourceMeta, val data: T)
//
// Collection containing Collection meta Data and a Array of Ressources deifned as above
data class Collection<T>(val meta: CollectionMeta?,val data: Array<Ressource<T>?>?)

object RadicalImageTypes {
    val svg = "image/svg+xml"
    val png = "image/png"
}