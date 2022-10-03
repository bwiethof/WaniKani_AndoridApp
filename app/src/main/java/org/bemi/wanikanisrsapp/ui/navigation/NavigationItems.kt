package org.bemi.wanikanisrsapp.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavigationItem(var title: String, var icon: ImageVector, var route: String) {
    object Home : NavigationItem("Home", Icons.Filled.Home, "home")
    object Dashbaord : NavigationItem("Dashboard", Icons.Filled.Favorite, "dashboard")
    object Profile : NavigationItem("Profile", Icons.Filled.Person, "profile")
}
