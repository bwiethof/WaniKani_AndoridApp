package org.bemi.wanikanisrsapp

object TokenStore {
    var token : String? = null

    fun get() : String? {return token}
    fun set(value: String) {
        token = value}
}