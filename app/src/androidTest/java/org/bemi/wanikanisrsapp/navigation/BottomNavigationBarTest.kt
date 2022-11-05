package org.bemi.wanikanisrsapp.navigation

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import org.bemi.wanikanisrsapp.BottomNavigationBar
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class BottomNavigationBarTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    lateinit var navController: TestNavHostController

    @Before
    fun setupBottomNavigationBarWithNavHost() {

        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            // Sets a ComposeNavigator to the navController so it can navigate through composables
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            WaniKaniNavHost(navController = navController)
            BottomNavigationBar(
                selectableScreens = navigationItems,
                onTabSelected = { screen -> navController.navigateSingleTopTo(screen.route) },
                currentScreen = Dashboard
            )
        }
    }

    @Test
    fun waniKaniNavHost_verifyDashboardStartDestination() {
        composeTestRule
            .onNodeWithContentDescription("Dashboard Screen")
            .assertIsDisplayed()
    }

    @Test
    fun waniKaniBottomNavigationBar_verifyDashboardIsSelectedOnStartup() {
        composeTestRule
            .onNodeWithContentDescription(Dashboard.title)
            .assertIsSelected()
    }

    @Test
    fun waniKaniBottomNavigationBar_clickProfile_navigatesToProfile() {

        composeTestRule
            .onNodeWithContentDescription(Profile.title)
            .performClick()

        composeTestRule
            .onNodeWithContentDescription("Profile Screen")
            .assertIsDisplayed()
    }

    @Test
    fun waniKaniBottomNavigationBar_clickHome_navigatesToHome() {

        composeTestRule
            .onNodeWithContentDescription(Home.title)
            .performClick()

        composeTestRule
            .onNodeWithContentDescription("Home Screen")
            .assertIsDisplayed()
    }

}