package org.bemi.wanikanisrsapp.data.repositories

import android.util.Log
import androidx.datastore.core.DataStore
import client.WaniKaniClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import model.User
import org.bemi.wanikanisrsapp.models.UserData
import org.bemi.wanikanisrsapp.user.Profile
import org.bemi.wanikanisrsapp.user.Subscription
import org.bemi.wanikanisrsapp.user.Token
import java.io.IOException
import java.time.LocalDateTime
import java.time.ZoneOffset
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext


class UserRepository @Inject constructor(
    private val profileStore: DataStore<Profile>,
    private val subscriptionStore: DataStore<Subscription>,
    private val tokenStore: DataStore<Token>,
    private val client: WaniKaniClient
) : CoroutineScope {

    private val loggingTag: String = "UserRepository"
    private val scope = CoroutineScope(coroutineContext)

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    private val profileFlow: Flow<Profile> = profileStore.data.catch { exception ->
        if (exception is IOException) {
            Log.e(loggingTag, "Error reading User Profile.", exception)
            emit(Profile.getDefaultInstance())
        } else {
            throw exception
        }
    }

    private val tokenFlow: Flow<Token> = tokenStore.data.catch { exception ->
        if (exception is IOException) {
            Log.e(loggingTag, "Error reading User Token.", exception)
            emit(Token.getDefaultInstance())
        } else {
            throw exception
        }
    }

    private val subscriptionFlow: Flow<Subscription> =
        subscriptionStore.data.catch { exception ->
            if (exception is IOException) {
                Log.e(loggingTag, "Error reading User Subscription.", exception)
                emit(Subscription.getDefaultInstance())
            } else {
                throw exception
            }
        }

    suspend fun getUserDataFromCache(): UserData {
        val cachedProfile = profileFlow.first()
        val cachedSubscription = subscriptionFlow.first()
        return UserData(cachedProfile, cachedSubscription)
    }

    suspend fun getUserData(): UserData {
        // user will be null or expired data in case the call to wanikani fails, this is desired behavior.
        // Cached data should rather be returned than an error in case of failure.
        try {

            val user = UserData.fromWaniKaniUser(fetchRemoteUser())

            val job = scope.launch {
                updateStoredUserProfile(user.profile)
                updateUserSubscription(user.subscription)
            }
            job.join()

            return user
        } catch (exception: Exception) {
            Log.e(loggingTag, "Error getting User Profile from WaniKani.", exception)

            return getUserDataFromCache()
        }
    }

    private suspend fun fetchRemoteUser(): User {
        return client.getUser().data
    }

    private suspend fun updateStoredUserProfile(profile: Profile?) {
        profile?.let {
            profileStore.updateData {
                it
            }
        }

    }

    suspend fun getStoredUserToken(): Token {
        return try {
            val cachedToken = tokenFlow.first()
            client.setNewToken(cachedToken.token)
            cachedToken
        } catch (exception: Exception) {
            Log.e(loggingTag, "Error getting User Token from Proto Datastore.", exception)
            Token.newBuilder().setToken("").setLastUpdated(
                LocalDateTime.now().toEpochSecond(
                    ZoneOffset.UTC
                )
            ).build()
        }
    }

    suspend fun setToken(userToken: String) {
        tokenStore.updateData { token ->
            token.toBuilder().setToken(userToken).setLastUpdated(
                LocalDateTime.now().toEpochSecond(
                    ZoneOffset.UTC
                )
            ).build()
        }
        client.setNewToken(userToken)
    }

    private suspend fun updateUserSubscription(subscription: Subscription?) {
        subscription?.let {
            subscriptionStore.updateData {
                it
            }
        }
    }

}
