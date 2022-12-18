package org.bemi.wanikanisrsapp.ui

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import org.bemi.wanikanisrsapp.BottomNavigationBar
import org.bemi.wanikanisrsapp.navigation.*
import org.junit.Rule
import org.junit.Test

class BottomBarTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private fun setupBottomBar(currentScreen: AppDestination = Dashboard) {
        composeTestRule.setContent {
            BottomNavigationBar(
                selectableScreens = navigationItems,
                onTabSelected = { },
                currentScreen = currentScreen
            )
        }
    }

    @Test
    fun waniKaniBottomBar_verifyDashboardSelected() {
        setupBottomBar()
        composeTestRule
            .onNodeWithContentDescription(Dashboard.title)
            .assertIsSelected()
    }

    @Test
    fun waniKaniBottomBar_verifyProfileSelected() {
        setupBottomBar(Profile)
        composeTestRule
            .onNodeWithContentDescription(Profile.title)
            .assertIsSelected()
    }

    @Test
    fun waniKaniBottomBar_verifyHomeSelected() {
        setupBottomBar(Home)
        composeTestRule
            .onNodeWithContentDescription(Home.title)
            .assertIsSelected()
    }

    @Test
    fun waniKaniBottomBar_destinationLabelExists() {
        setupBottomBar()

        composeTestRule
            .onNode(
                hasText(Dashboard.title) and
                        hasParent(
                            hasContentDescription(Dashboard.title)
                        ),
                useUnmergedTree = true
            )
            .assertExists()
    }

}