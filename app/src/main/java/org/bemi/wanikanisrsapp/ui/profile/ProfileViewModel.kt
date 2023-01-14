package org.bemi.wanikanisrsapp.ui.profile

import androidx.lifecycle.ViewModel
import org.bemi.wanikanisrsapp.data.MockData
import org.bemi.wanikanisrsapp.data.UserData
import java.time.LocalDateTime
import java.time.ZoneOffset

typealias UserInfoItems = Map<String, String>

class ProfileViewModel : ViewModel() {
    private val userData: UserData = getUserData()
    private var cashedUserData: UserData? = null // will be replaced with call to repository

    private fun getUserData(): UserData {
        val userData = getStoredUserData()

        if (userData.user == null || isUserDataExpired()) {
            // TODO: Get user data from WaniKani API
            // user will be null or expired data in case the call to wanikani fails, this is desired behavior.
            // Cached data should rather be returned than an error in case of failure.
            userData.updateUser(MockData().mockUser)
            // TODO: Store user data in repository
            cashedUserData = userData
        }

        return userData
    }

    private fun getStoredUserData(): UserData {
        // TODO: try read data from repository
        if (cashedUserData != null) {
            return cashedUserData!!
        }
        // otherwise return empty user data
        return UserData()
    }

    // has to be extended with proper token validation logic
    fun isTokenValid(token: String?): Boolean {
        return !token.isNullOrBlank()
    }

    fun tokenExists(): Boolean {
        return userData.token.isNotBlank()
    }

    fun getUserToken(): String {
        return userData.token
    }

    fun updateToken(text: String) {
        userData.token = text // TODO: Store user data in repository
    }

    fun getUserProfile(): UserInfoItems {
        if (userData.user != null) {
            return userData.user!!.let {
                mapOf(
                    "Username" to it.username,
                    "Id" to (it.id),
                    "Vacation Started At" to it.current_vacation_started_at,
                    "Level" to it.level.toString(),
                    "Profile URL" to it.profile_url,
                    "Started At" to it.started_at
                )
            }
        }
        return emptyMap()
    }

    fun getUserSubscription(): UserInfoItems {
        if (userData.user != null) {
            return userData.user!!.let {
                mapOf(
                    "Active" to it.subscription.active.toString(),
                    "Max Level Granted" to it.subscription.max_level_granted.toString(),
                    "Period ends at" to it.subscription.period_ends_at.toString(),
                    "Type" to it.subscription.type
                )
            }
        }
        return emptyMap()
    }

    fun isUserDataAvailable(): Boolean {
        if (userData.user != null || tokenExists()) return true
        return false
    }

    private fun isUserDataExpired(): Boolean {
        return LocalDateTime.now()
            .toEpochSecond(ZoneOffset.UTC) - userData.lastUpdated.toEpochSecond(ZoneOffset.UTC) > 86400
    }

}