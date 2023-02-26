package org.bemi.wanikanisrsapp.data.dataStore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import org.bemi.wanikanisrsapp.user.Profile
import org.bemi.wanikanisrsapp.user.Subscription
import org.bemi.wanikanisrsapp.user.Token
import java.io.InputStream
import java.io.OutputStream

object UserProfileSerializer : Serializer<Profile> {
    override val defaultValue: Profile
        get() = Profile.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): Profile {
        try {
            return Profile.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read Proto.", exception)
        } catch (exception: Exception) {
            throw RuntimeException("Error reading Profile from Data Store", exception)
        }
    }

    override suspend fun writeTo(t: Profile, output: OutputStream) = t.writeTo(output)
}


object UserTokenSerializer : Serializer<Token> {
    override val defaultValue: Token
        get() = Token.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): Token {
        try {
            return Token.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read Proto.", exception)
        } catch (exception: Exception) {
            throw RuntimeException("Error reading Token from Data Store", exception)
        }
    }

    override suspend fun writeTo(t: Token, output: OutputStream) = t.writeTo(output)
}


object UserSubscriptionSerializer : Serializer<Subscription> {
    override val defaultValue: Subscription
        get() = Subscription.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): Subscription {
        try {
            return Subscription.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read Proto.", exception)
        }
    }

    override suspend fun writeTo(t: Subscription, output: OutputStream) = t.writeTo(output)
}
