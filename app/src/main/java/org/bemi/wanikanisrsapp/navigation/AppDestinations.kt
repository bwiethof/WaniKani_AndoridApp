package org.bemi.wanikanisrsapp.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

interface AppDestination {
    val title: String
    val icon: ImageVector
    val route: String
}


object Dashboard : AppDestination {
    override val title = "Dashboard"
    override val icon = Icons.Filled.Favorite
    override val route = "dashboard"
}

object Home : AppDestination {
    override val title = "Home"
    override val icon = Icons.Filled.Home
    override val route = "home"
}

object Profile : AppDestination {
    override val title = "Profile"
    override val icon = Icons.Filled.Person
    override val route = "profile"
}

val navigationItems = listOf(Profile, Dashboard, Home)