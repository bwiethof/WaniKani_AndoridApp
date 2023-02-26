package org.bemi.wanikanisrsapp.data.dataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.bemi.wanikanisrsapp.user.Profile
import org.bemi.wanikanisrsapp.user.Subscription
import org.bemi.wanikanisrsapp.user.Token
import javax.inject.Singleton

val Context.profileDataStore: DataStore<Profile> by dataStore(
    fileName = "userProfile.pb", serializer = UserProfileSerializer
)
val Context.tokenDataStore: DataStore<Token> by dataStore(
    fileName = "userToken.pb", serializer = UserTokenSerializer
)
val Context.subscriptionDataStore: DataStore<Subscription> by dataStore(
    fileName = "userSubscription.pb", serializer = UserSubscriptionSerializer
)

@Module
@InstallIn(SingletonComponent::class)
object DataStoreProvider {

    @Provides
    @Singleton
    fun providerUserProfile(@ApplicationContext appContext: Context): DataStore<Profile> {
        return appContext.profileDataStore
    }

    @Provides
    @Singleton
    fun providerUserSubscriptions(@ApplicationContext appContext: Context): DataStore<Subscription> {
        return appContext.subscriptionDataStore
    }

    @Provides
    @Singleton
    fun providerUserToken(@ApplicationContext appContext: Context): DataStore<Token> {
        return appContext.tokenDataStore
    }
}
