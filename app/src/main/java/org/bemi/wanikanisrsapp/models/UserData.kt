package org.bemi.wanikanisrsapp.models


import model.User
import org.bemi.wanikanisrsapp.user.Profile
import org.bemi.wanikanisrsapp.user.Subscription
import java.time.LocalDateTime
import java.time.ZoneOffset

class UserData() {

    constructor(p: Profile, s: Subscription) : this() {
        profile = p
        subscription = s
    }

    var profile: Profile? = null
    var subscription: Subscription? = null

    companion object {

        fun fromWaniKaniUser(user: User): UserData {
            val profile: Profile = Profile.newBuilder().setProfileUrl(user.profile_url)
                .setCurrentVacationStartedAt(user.current_vacation_started_at.orEmpty())
                .setStartedAt(user.started_at).setId(user.id).setLevel(user.level)
                .setUsername(user.username).setLastUpdated(
                    LocalDateTime.now().toEpochSecond(
                        ZoneOffset.UTC
                    )
                ).build()
            val subscription: Subscription =
                Subscription.newBuilder().setActive(user.subscription.active)
                    .setType(user.subscription.type)
                    .setMaxLevelGranted(user.subscription.max_level_granted)
                    .setPeriodEndsAt(user.subscription.period_ends_at.orEmpty()).setLastUpdated(
                        LocalDateTime.now().toEpochSecond(
                            ZoneOffset.UTC
                        )
                    ).build()

            return UserData(profile, subscription)
        }
    }
}
