package org.bemi.wanikanisrsapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.bemi.wanikanisrsapp.ui.dashboard.DashboardScreen
import org.bemi.wanikanisrsapp.ui.home.HomeScreen
import org.bemi.wanikanisrsapp.ui.profile.ProfileScreen
import org.bemi.wanikanisrsapp.ui.startUp.EnterTokenScreen
import javax.inject.Singleton

@Composable
@Singleton
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Dashboard.route,
        modifier = modifier
    ) {
        composable(route = Profile.route) {
            ProfileScreen(onNavigateToTokenScreenClicked = { navController.navigateSingleTopTo(Token.route) })
        }
        composable(route = Dashboard.route) { DashboardScreen() }
        composable(route = Home.route) { HomeScreen() }
        composable(route = Token.route) {
            EnterTokenScreen(onContinueClicked = {
                navController.navigateSingleTopTo(Profile.route)
            })
        }
    }
}

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        // Pop up to the start destination of the graph to
        // avoid building up a large stack of destinations
        // on the back stack as users select items
        /*        popUpTo(
                    this@navigateSingleTopTo.graph.findStartDestination().id
                ) {
                    saveState = true
                }*/
        // Avoid multiple copies of the same destination when
        // reselecting the same item
        launchSingleTop = true
        // Restore state when reselecting a previously selected item
        restoreState = true
    }
