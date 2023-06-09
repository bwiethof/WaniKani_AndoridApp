package org.bemi.wanikanisrsapp.data.client

import client.WaniKaniClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class WaniKaniClientProvider {

    val token = "a7543476-9981-49c7-905b-3c316acee7f7"

    @Provides
    @Singleton
    fun provideKtorWaniKaniClient(): WaniKaniClient {
        return WaniKaniClient("")
    }
}
