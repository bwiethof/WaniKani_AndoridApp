@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)

package org.bemi.wanikanisrsapp.ui.mainCompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.consumedWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.android.material.elevation.SurfaceColors
import org.bemi.wanikanisrsapp.ui.mainCompose.ui.theme.WaniKaniSRSAppTheme

class JcDashboardActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WaniKaniSRSAppTheme {
                val color = SurfaceColors.SURFACE_2.getColor(this)
                window.statusBarColor = color

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = MaterialTheme.colorScheme.background,
                    topBar = {
                        SetupTopBar(testTitle = "Test")
                    },
                    content = { innerPadding ->
                        LazyColumn(
                            // consume insets as scaffold doesn't do it by default (yet)
                            modifier = Modifier.consumedWindowInsets(innerPadding),
                            contentPadding = innerPadding
                        ) {
                            item { Greeting("Android") }
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun SetupScuffold() {
    var selectedItem by remember { mutableStateOf(1) }
    val items = listOf("?", "Dashboard", "Profile")
    val icons = listOf(Icons.Filled.Favorite, Icons.Filled.Home, Icons.Filled.Person)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            SetupTopBar(testTitle = "Test")
        },
        bottomBar = {
            NavigationBar {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedItem == index,
                        onClick = { selectedItem = index },
                        icon = { Icon(icons[index], contentDescription = item) },
                        label = { Text(item) }
                    )
                }
            }
        },
        content = { innerPadding ->
            LazyColumn(
                // consume insets as scaffold doesn't do it by default (yet)
                modifier = Modifier.consumedWindowInsets(innerPadding),
                contentPadding = innerPadding
            ) {
                item { Greeting("Android") }
                // TODO: Switch content depending on navigation bar
                // https://developer.android.com/jetpack/compose/navigation
            }
        }

    )
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

// TopBar
@Composable
fun SetupTopBar(testTitle: String) {
    CenterAlignedTopAppBar(title = { Text(text = testTitle) })
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {

    WaniKaniSRSAppTheme {
        SetupScuffold()
    }
}

@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
    WaniKaniSRSAppTheme {
        SetupTopBar("Test Title")
    }
}

@Preview(showBackground = true)
@Composable
fun NavigationBarPreview() {
    var selectedItem by remember { mutableStateOf(1) }
    val items = listOf("?", "Dashboard", "Profile")
    val icons = listOf(Icons.Filled.Favorite, Icons.Filled.Home, Icons.Filled.Person)

    WaniKaniSRSAppTheme {
        NavigationBar {
            items.forEachIndexed { index, item ->
                NavigationBarItem(
                    selected = selectedItem == index,
                    onClick = { selectedItem = index },
                    icon = { Icon(icons[index], contentDescription = item) },
                    label = { Text(item) }
                )
            }
        }
    }
}