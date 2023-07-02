package org.bemi.wanikanisrsapp.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.bemi.wanikanisrsapp.data.repositories.UserRepository
import org.bemi.wanikanisrsapp.models.UserData
import org.bemi.wanikanisrsapp.user.Profile
import org.bemi.wanikanisrsapp.user.Subscription
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext


typealias UserInfoItems = Map<String, String?>

data class UserDataState(
    val userData: UserData = UserData(),
    var updated: Boolean = false
)

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel(), CoroutineScope {

    private val _uiState = MutableStateFlow(UserDataState())
    val uiState: StateFlow<UserDataState> = _uiState.asStateFlow()

    private var userData = UserData()
    private var job = Job()
    private val scope = CoroutineScope(coroutineContext)

    init {
        viewModelScope.launch {
            getCachedUserData()
            fetchRemoteUserData()
        }
    }

    private suspend fun fetchRemoteUserData() {
        if (userData.profile == null || isUserDataExpired()) {
            println("Update user data from API")
            withContext(Dispatchers.IO) {
                userData = userRepository.getUserData()
                _uiState.update { current -> current.copy(userData = userData) }
            }
            _uiState.update { current -> current.copy(updated = true) }
        }
    }

    private suspend fun getCachedUserData() {
        withContext(Dispatchers.IO) {
            userData = userRepository.getUserDataFromCache()
            _uiState.update { current ->
                current.copy(userData = userData)
            }
            _uiState.update { current -> current.copy(updated = false) }

        }
        println("Read User Data from Cache")
    }

    fun toProfileMap(profile: Profile?): UserInfoItems {
        return profile.let {
            mapOf(
                "Username" to it?.username,
                "Id" to (it?.id),
                "Vacation Started At" to it?.currentVacationStartedAt,
                "Level" to it?.level.toString(),
                "Profile URL" to it?.profileUrl,
                "Started At" to (if (it?.startedAt.isNullOrBlank()) "" else LocalDateTime.parse(
                    it?.startedAt, DateTimeFormatter.ofPattern(_dateTimePattern)
                ).format(
                    DateTimeFormatter.ISO_LOCAL_DATE
                )),
                "Last Updated At" to LocalDateTime.ofEpochSecond(
                    (it?.lastUpdated ?: 0), 0, ZoneOffset.UTC
                ).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            )
        }
    }

    private val _dateTimePattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'"

    fun toSubscriptionMap(subscription: Subscription?): UserInfoItems {
        return subscription.let {
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

    private fun isUserDataExpired(): Boolean {
        val lastUpdated = userData.profile?.lastUpdated ?: 0
        return LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) - lastUpdated > 86400
    }

    override val coroutineContext: CoroutineContext
        get() = job + viewModelScope.coroutineContext + Dispatchers.IO

}
