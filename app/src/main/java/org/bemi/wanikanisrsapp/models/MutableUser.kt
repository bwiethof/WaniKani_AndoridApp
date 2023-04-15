package org.bemi.wanikanisrsapp.models

import models.User

class MutableUser {
    var currentVacationStartedAt: String = ""
    var id: String = ""
    var level: Int = 0
    lateinit var preferences: User.Preferences
    var profileUrl: String = ""
    var startedAt: String = ""
    lateinit var subscription: User.Subscription
    var username: String = ""

    fun MutableUser.toImmutable() = User(
        currentVacationStartedAt,
        id,
        level,
        preferences,
        profileUrl,
        startedAt,
        subscription,
        username
    )
}

fun User.toMutable() = this.let {
    MutableUser().apply {
        currentVacationStartedAt = it.current_vacation_started_at
        id = it.id
        level = it.level
        preferences = it.preferences
        profileUrl = it.profile_url
        startedAt = it.started_at
        subscription = it.subscription
        username = it.username
    }
}
