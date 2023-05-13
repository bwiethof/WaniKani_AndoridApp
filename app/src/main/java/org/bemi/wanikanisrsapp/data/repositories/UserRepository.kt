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
    private val userProfileStore: DataStore<Profile>,
    private val userSubscriptionStore: DataStore<Subscription>,
    private val userTokenStore: DataStore<Token>,
    private val client: WaniKaniClient
) : CoroutineScope {

    private val loggingTag: String = "UserRepository"
    private val scope = CoroutineScope(coroutineContext)

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    private val userProfileFlow: Flow<Profile> = userProfileStore.data.catch { exception ->
        if (exception is IOException) {
            Log.e(loggingTag, "Error reading User Profile.", exception)
            emit(Profile.getDefaultInstance())
        } else {
            throw exception
        }
    }

    private val userTokenFlow: Flow<Token> = userTokenStore.data.catch { exception ->
        if (exception is IOException) {
            Log.e(loggingTag, "Error reading User Token.", exception)
            emit(Token.getDefaultInstance())
        } else {
            throw exception
        }
    }

    private val userSubscriptionFlow: Flow<Subscription> =
        userSubscriptionStore.data.catch { exception ->
            if (exception is IOException) {
                Log.e(loggingTag, "Error reading User Subscription.", exception)
                emit(Subscription.getDefaultInstance())
            } else {
                throw exception
            }
        }

    suspend fun getUserDataFromCache(): UserData {
        val cachedProfile = userProfileFlow.first()
        val cachedSubscription = userSubscriptionFlow.first()
        return UserData(cachedProfile, cachedSubscription)
    }

    suspend fun getUserData(): UserData {
        // user will be null or expired data in case the call to wanikani fails, this is desired behavior.
        // Cached data should rather be returned than an error in case of failure.
        try {

            val user = UserData.fromWaniKaniUser(getUserDataFromWaniKani())

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

    private suspend fun getUserDataFromWaniKani(): User {
        val user = client.getUser()
        return user.data
    }

    private suspend fun updateStoredUserProfile(profile: Profile?) {
        if (profile != null) {
            userProfileStore.updateData {
                profile
            }
        }

    }

    suspend fun getStoredUserToken(): Token {
        return try {
            userTokenFlow.first()
        } catch (exception: Exception) {
            Log.e(loggingTag, "Error getting User Token from Proto Datastore.", exception)
            Token.newBuilder().setToken("").setLastUpdated(
                LocalDateTime.now().toEpochSecond(
                    ZoneOffset.UTC
                )
            ).build()
        }
    }

    suspend fun updateStoredUserToken(userToken: String) {
        userTokenStore.updateData { token ->
            token.toBuilder().setToken(userToken).setLastUpdated(
                LocalDateTime.now().toEpochSecond(
                    ZoneOffset.UTC
                )
            ).build()
        }
    }

    private suspend fun updateUserSubscription(userSubscription: Subscription?) {
        if (userSubscription != null) {
            userSubscriptionStore.updateData {
                userSubscription
            }
        }
    }

}
