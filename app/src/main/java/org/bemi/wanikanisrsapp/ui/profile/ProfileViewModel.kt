package org.bemi.wanikanisrsapp.ui.profile

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.bemi.wanikanisrsapp.data.repositories.UserRepository
import org.bemi.wanikanisrsapp.models.MockData
import org.bemi.wanikanisrsapp.models.UserData
import java.time.LocalDateTime
import java.time.ZoneOffset
import javax.inject.Inject

typealias UserInfoItems = Map<String, String?>


@HiltViewModel
class ProfileViewModel @Inject constructor(private val userRepository: UserRepository) :
    ViewModel() {

    private var token: String = ""
    private var userData: UserData = UserData()

    init {
        updateTokenFromCache()
        updateUserDataFromCache()
        updateUserDataFromAPI()
    }

    private fun updateUserDataFromAPI() {
        if (userData.user == null || isUserDataExpired()) {
            // TODO: Get user data from WaniKani API
            // user will be null or expired data in case the call to wanikani fails, this is desired behavior.
            // Cached data should rather be returned than an error in case of failure.
            runBlocking { userRepository.updateUserProfile(MockData().mockUser) }
            runBlocking { userRepository.updateUserSubscription(MockData().mockUser.subscription) }

            updateUserDataFromCache()
        }
    }

    private fun updateUserDataFromCache() {
        val cachedUser = runBlocking { userRepository.userProfileFlow.first() }
        val cachedSubscription = runBlocking { userRepository.userSubscriptionFlow.first() }

        userData = UserData(cachedUser, cachedSubscription)
    }


    private fun updateTokenFromCache() {
        token = runBlocking { userRepository.userTokenFlow.first().token }
    }

    // has to be extended with proper token validation logic
    fun isTokenValid(token: String?): Boolean {
        return !token.isNullOrBlank()
    }

    fun tokenExists(): Boolean {
        return token.isNotBlank()
    }

    fun updateToken(text: String) {
        runBlocking { userRepository.updateUserToken(text) }
        updateTokenFromCache()
        updateUserDataFromAPI()
    }

    fun getUserProfile(): UserInfoItems {
        if (userData.user != null) {
            return userData.user.let {
                mapOf(
                    "Username" to it?.username,
                    "Id" to (it?.id),
                    "Vacation Started At" to it?.currentVacationStartedAt,
                    "Level" to it?.level.toString(),
                    "Profile URL" to it?.profileUrl,
                    "Started At" to it?.startedAt,
                    "Last Updated At" to LocalDateTime.ofEpochSecond(
                        (it?.lastUpdated ?: 0), 0, ZoneOffset.UTC
                    ).toString(),
                    "API Token" to token
                )
            }
        }
        return emptyMap()
    }

    fun getUserSubscription(): UserInfoItems {
        if (userData.subscription != null) {
            return userData.subscription.let {
                mapOf(
                    "Active" to it?.active.toString(),
                    "Max Level Granted" to it?.maxLevelGranted.toString(),
                    "Period ends at" to it?.periodEndsAt.toString(),
                    "Type" to it?.type,
                    "Last Updated At" to LocalDateTime.ofEpochSecond(
                        (it?.lastUpdated ?: 0), 0, ZoneOffset.UTC
                    ).toString()
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
        val lastUpdated = userData.user?.lastUpdated ?: 0
        return LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) - lastUpdated > 86400
    }

}
