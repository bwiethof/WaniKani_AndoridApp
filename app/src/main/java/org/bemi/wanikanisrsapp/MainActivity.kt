@file:OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class
)

package org.bemi.wanikanisrsapp

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import org.bemi.wanikanisrsapp.navigation.AppDestination
import org.bemi.wanikanisrsapp.navigation.AppNavHost
import org.bemi.wanikanisrsapp.navigation.Dashboard
import org.bemi.wanikanisrsapp.navigation.Token
import org.bemi.wanikanisrsapp.navigation.bottomBatItems
import org.bemi.wanikanisrsapp.navigation.navigateSingleTopTo
import org.bemi.wanikanisrsapp.ui.startUp.TokenViewModel
import org.bemi.wanikanisrsapp.ui.theme.WaniKaniSRSAppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WaniKaniSRSAppTheme {
                SrsApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SrsApp(srsViewModel: TokenViewModel = hiltViewModel()) {
    val navController = rememberNavController()
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStack?.destination
    val currentScreen =
        bottomBatItems.find { it.route == currentDestination?.route } ?: Dashboard

    val uiState by srsViewModel.uiState.collectAsState()
    var hasToken by rememberSaveable {
        mutableStateOf(uiState.tokenExists)
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBar(testTitle = currentScreen.title)
        },
        bottomBar = {
            if (shouldShowBottomBar(navController = navController)) {
                BottomNavigationBar(
                    selectableScreens = bottomBatItems,
                    onTabSelected = { screen ->
                        navController.navigateSingleTopTo(screen.route)
                    },
                    currentScreen = currentScreen
                )
            }
        },
    ) { innerPadding ->
        AppNavHost(
            navController = navController,
            modifier = Modifier.padding(innerPadding),
        )
        if (!hasToken) {

            println("No Token in Startup")

            LaunchedEffect(uiState) {
                srsViewModel.fetchUserToken()
                if (!uiState.tokenExists) {
                    println("No Token in cache")
                    navController.navigateSingleTopTo(Token.route)
                    return@LaunchedEffect
                }
                hasToken = true
            }
        }
    }

}


@Composable
fun shouldShowBottomBar(navController: NavController): Boolean {
    return navController.currentBackStackEntryAsState().value?.destination?.route in bottomBatItems.map { item -> item.route }
}

// TopBar
@Composable
fun TopBar(testTitle: String) {
    CenterAlignedTopAppBar(
        title = { Text(text = testTitle) },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors()
    )
}

@Composable
fun BottomNavigationBar(
    selectableScreens: List<AppDestination>,
    onTabSelected: (AppDestination) -> Unit,
    currentScreen: AppDestination
) {
    NavigationBar {
        selectableScreens.forEach { screen ->
            NavigationBarItem(
                selected = currentScreen == screen,
                onClick = {
                    onTabSelected(screen)
                },
                icon = { Icon(screen.icon, contentDescription = screen.title) },
                label = { Text(screen.title) },
                modifier = Modifier.semantics {
                    contentDescription = screen.title
                }
            )
        }
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
        SrsApp()
    }
}

@Preview(
    showBackground = true,
    name = "DefaultPreviewLight"
)
@Composable
fun DefaultPreviewLight() {
    WaniKaniSRSAppTheme {
        SrsApp()
    }
}
