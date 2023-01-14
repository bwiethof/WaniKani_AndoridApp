package org.bemi.wanikanisrsapp.models


import org.bemi.wanikanisrsapp.user.Profile
import org.bemi.wanikanisrsapp.user.Subscription

class UserData() {

    constructor(p: Profile, s: Subscription) : this() {
        user = p
        subscription = s
    }

    var user: Profile? = null
    var subscription: Subscription? = null
}
