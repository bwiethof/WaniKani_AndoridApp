package org.bemi.wanikanisrsapp.models

import model.User

class MockData {

    val mockUser = User(
        "never",
        "123456789",
        5,
        User.Preferences(0, false, 0, "", false, false),
        "myUrl",
        "yesterday",
        User.Subscription(true, 100, "anyday", "lifetime"),
        "Chelly"
    )
}
