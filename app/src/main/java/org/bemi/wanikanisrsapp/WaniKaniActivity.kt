@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)

package org.bemi.wanikanisrsapp

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
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.bemi.wanikanisrsapp.navigation.Dashboard
import org.bemi.wanikanisrsapp.navigation.WaniKaniNavHost
import org.bemi.wanikanisrsapp.navigation.navigateSingleTopTo
import org.bemi.wanikanisrsapp.navigation.navigationItems
import org.bemi.wanikanisrsapp.ui.theme.WaniKaniSRSAppTheme

class WaniKaniActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WaniKaniSRSAppTheme {
                WaniKaniApp()
            }
        }
    }
}

@Composable
fun WaniKaniApp() {
    val navController = rememberNavController()
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStack?.destination
    val currentScreen =
        navigationItems.find { it.route == currentDestination?.route } ?: Dashboard
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBar(testTitle = "Test")
        },
        bottomBar = {
            NavigationBar {
                navigationItems.forEach { screen ->
                    NavigationBarItem(
                        selected = currentScreen == screen,
                        onClick = {
                            navController.navigateSingleTopTo(screen.route)
                        },
                        icon = { Icon(screen.icon, contentDescription = screen.title) },
                        label = { Text(screen.title) }
                    )
                }
            }
        },
        content = { innerPadding ->
            WaniKaniNavHost(
                navController = navController,
                modifier = Modifier.padding(innerPadding),

                )
        }
    )
}

// TopBar
@Composable
fun TopBar(testTitle: String) {
    CenterAlignedTopAppBar(title = { Text(text = testTitle) })
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark"
)
@Composable
fun DefaultPreviewDark() {
    WaniKaniSRSAppTheme {
        WaniKaniApp()
    }
}

@Preview(
    showBackground = true,
    name = "DefaultPreviewLight"
)
@Composable
fun DefaultPreviewLight() {
    WaniKaniSRSAppTheme {
        WaniKaniApp()
    }
}
