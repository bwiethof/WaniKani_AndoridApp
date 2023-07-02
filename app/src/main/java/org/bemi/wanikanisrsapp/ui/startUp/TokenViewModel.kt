package org.bemi.wanikanisrsapp.ui.startUp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.bemi.wanikanisrsapp.data.repositories.UserRepository
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

data class TokenState(
    val tokenExists: Boolean = false,
    val tokenUpdated: Boolean = false
)

@HiltViewModel
class TokenViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel(), CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO
    private val scope = CoroutineScope(coroutineContext)

    private val _uiState = MutableStateFlow(TokenState())
    val uiState: StateFlow<TokenState> = _uiState.asStateFlow()

    init {
        scope.launch {
            fetchUserToken()
        }
    }

    suspend fun fetchUserToken(): Boolean {
        println("Get Token from cache")
        val token =
            userRepository.getStoredUserToken()

        val tokenExists = isTokenValid(token.token)
        if (tokenExists) {
            println("Found Token in cache updating UiState")
            _uiState.update { currentState ->
                currentState.copy(
                    tokenExists = true,
                    tokenUpdated = true
                )
            }
            preloadUserData()
            return true
        }
        return false
    }


    private fun preloadUserData() {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.getUserData()
        }
    }

    // TODO: has to be extended with proper token validation logic
    fun isTokenValid(token: String?): Boolean {
        return !token.isNullOrBlank()
    }

    suspend fun updateToken(newToken: String) {
        userRepository.setToken(newToken)
        fetchUserToken()
    }

}
