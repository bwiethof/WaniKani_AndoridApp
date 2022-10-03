package org.bemi.wanikanisrsapp.data

object TokenStore {
    var token: String? = null

    fun isTokenValid(token: String?): Boolean {
        return !token.isNullOrBlank()
    }

    fun tokenExists(): Boolean {
        return !token.isNullOrBlank()
    }
}