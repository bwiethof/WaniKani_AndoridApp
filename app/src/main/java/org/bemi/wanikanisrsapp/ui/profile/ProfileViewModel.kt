package org.bemi.wanikanisrsapp.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.bemi.wanikanisrsapp.data.repositories.UserRepository
import org.bemi.wanikanisrsapp.models.UserData
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext


typealias UserInfoItems = Map<String, String?>


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel(), CoroutineScope {

    private var token: String = ""
    private var userData: UserData = UserData()
    private var job = Job()
    private val scope = CoroutineScope(coroutineContext)

    init {
        runBlocking {
            getTokenFromCache()
            getUserDataFromCache()
            updateUserDataFromAPI()
        }
    }


    private fun updateUserDataFromAPI() {
        if (userData.profile == null || isUserDataExpired()) {
            println("inside update user")
            scope.launch { userData = userRepository.getUserData() }
        }
    }


    private fun getUserDataFromCache() {
        scope.launch {
            userData = userRepository.getUserDataFromCache()
        }
    }


    private suspend fun getTokenFromCache() {
        token = userRepository.getStoredUserToken().token
        println("Read Token $token from Cache")
    }

    // has to be extended with proper token validation logic
    fun isTokenValid(token: String?): Boolean {
        return !token.isNullOrBlank()
    }

    fun tokenExists(): Boolean {
        return token.isNotBlank()
    }

    suspend fun updateToken(text: String) {
        userRepository.updateStoredUserToken(text)
        getTokenFromCache()
        updateUserDataFromAPI()
    }

    fun getUserProfile(): UserInfoItems {
        if (userData.profile != null) {
            return userData.profile.let {
                mapOf(
                    "Username" to it?.username,
                    "Id" to (it?.id),
                    "Vacation Started At" to it?.currentVacationStartedAt,
                    "Level" to it?.level.toString(),
                    "Profile URL" to it?.profileUrl,
                    "Started At" to LocalDateTime.parse(
                        it?.startedAt,
                        DateTimeFormatter.ofPattern(DATE_TIME_PATTERN)
                    ).format(
                        DateTimeFormatter.ISO_LOCAL_DATE
                    ),
                    "Last Updated At" to LocalDateTime.ofEpochSecond(
                        (it?.lastUpdated ?: 0), 0, ZoneOffset.UTC
                    ).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                    "API Token" to token
                )
            }
        }
        return emptyMap()
    }

    private val DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'"

    fun getUserSubscription(): UserInfoItems {
        if (userData.subscription != null) {
            return userData.subscription.let {
                mapOf(
                    "Active" to it?.active.toString(),
                    "Max Level Granted" to it?.maxLevelGranted.toString(),
                    "Period ends at" to it?.periodEndsAt?.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                    "Type" to it?.type,
                    "Last Updated At" to LocalDateTime.ofEpochSecond(
                        (it?.lastUpdated ?: 0), 0, ZoneOffset.UTC
                    ).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                )
            }
        }
        return emptyMap()
    }

    fun isUserDataAvailable(): Boolean {
        if (userData.profile != null || tokenExists()) return true
        return false
    }

    private fun isUserDataExpired(): Boolean {
        val lastUpdated = userData.profile?.lastUpdated ?: 0
        return LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) - lastUpdated > 86400
    }

    override val coroutineContext: CoroutineContext
        get() = job + viewModelScope.coroutineContext + Dispatchers.IO

}
