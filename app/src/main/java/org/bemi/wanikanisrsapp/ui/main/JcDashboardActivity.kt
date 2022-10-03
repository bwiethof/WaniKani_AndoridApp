@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)

package org.bemi.wanikanisrsapp.ui.main

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.bemi.wanikanisrsapp.ui.navigation.NavigationItem
import org.bemi.wanikanisrsapp.ui.theme.WaniKaniSRSAppTheme

class JcDashboardActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WaniKaniSRSAppTheme {
                MainScreenView()
            }
        }
    }
}

@Composable
fun MainScreenView() {
    val navController = rememberNavController()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBar(testTitle = "Test")
        },
        bottomBar = {
            BottomBar(navController)
        },
        content = { innerPadding ->
            NavigationGraph(
                Modifier.padding(innerPadding),
                navController
            )
        }
    )
}

// TopBar
@Composable
fun TopBar(testTitle: String) {
    CenterAlignedTopAppBar(title = { Text(text = testTitle) })
}

// NavigationBar
@Composable
fun BottomBar(navController: NavHostController) {
    val items = listOf(NavigationItem.Profile, NavigationItem.Dashbaord, NavigationItem.Home)

    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        items.forEach { screen ->
            NavigationBarItem(
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                    navController.navigate(screen.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items>
/*                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }*/
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                },
                icon = { Icon(screen.icon, contentDescription = screen.title) },
                label = { Text(screen.title) }
            )
        }
    }
}

@Composable
fun NavigationGraph(
    modifier: Modifier,
    navController: NavHostController,
    startDestination: String = NavigationItem.Dashbaord.route
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(NavigationItem.Profile.route) {
            ProfileScreen()
        }
        composable(NavigationItem.Dashbaord.route) { DashboardScreen() }
        composable(NavigationItem.Home.route) { HomeScreen() }
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark"
)
@Composable
fun DefaultPreviewDark() {
    WaniKaniSRSAppTheme {
        MainScreenView()
    }
}

@Preview(
    showBackground = true,
    name = "DefaultPreviewLight"
)
@Composable
fun DefaultPreviewLight() {
    WaniKaniSRSAppTheme {
        MainScreenView()
    }
}
