package org.bemi.wanikanisrsapp.data.repositories

import android.util.Log
import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import models.User
import org.bemi.wanikanisrsapp.user.Profile
import org.bemi.wanikanisrsapp.user.Subscription
import org.bemi.wanikanisrsapp.user.Token
import java.io.IOException
import java.time.LocalDateTime
import java.time.ZoneOffset
import javax.inject.Inject


class UserRepository @Inject constructor(
    private val userProfileStore: DataStore<Profile>,
    private val userSubscriptionStore: DataStore<Subscription>,
    private val userTokenStore: DataStore<Token>
) {

    private val loggingTag: String = "UserRepository"

    val userProfileFlow: Flow<Profile> = userProfileStore.data.catch { exception ->
        if (exception is IOException) {
            Log.e(loggingTag, "Error reading User Profile.", exception)
            emit(Profile.getDefaultInstance())
        } else {
            throw exception
        }
    }

    val userTokenFlow: Flow<Token> = userTokenStore.data.catch { exception ->
        if (exception is IOException) {
            Log.e(loggingTag, "Error reading User Profile.", exception)
            emit(Token.getDefaultInstance())
        } else {
            throw exception
        }
    }

    val userSubscriptionFlow: Flow<Subscription> = userSubscriptionStore.data.catch { exception ->
        if (exception is IOException) {
            Log.e(loggingTag, "Error reading User Profile.", exception)
            emit(Subscription.getDefaultInstance())
        } else {
            throw exception
        }
    }

    suspend fun updateUserProfile(user: User) {
        userProfileStore.updateData { profile ->
            profile.toBuilder().setProfileUrl(user.profile_url)
                .setCurrentVacationStartedAt(user.current_vacation_started_at)
                .setStartedAt(user.started_at).setId(user.id).setLevel(user.level)
                .setUsername(user.username).setLastUpdated(
                    LocalDateTime.now().toEpochSecond(
                        ZoneOffset.UTC
                    )
                ).build()
        }
    }

    suspend fun updateUserToken(userToken: String) {
        userTokenStore.updateData { token ->
            token.toBuilder().setToken(userToken).setLastUpdated(
                LocalDateTime.now().toEpochSecond(
                    ZoneOffset.UTC
                )
            ).build()
        }
    }

    suspend fun updateUserSubscription(userSubscription: User.Subscription) {
        userSubscriptionStore.updateData { subscription ->
            subscription.toBuilder().setActive(userSubscription.active)
                .setType(userSubscription.type)
                .setMaxLevelGranted(userSubscription.max_level_granted)
                .setPeriodEndsAt(userSubscription.period_ends_at).setLastUpdated(
                    LocalDateTime.now().toEpochSecond(
                        ZoneOffset.UTC
                    )
                ).build()
        }
    }

}
