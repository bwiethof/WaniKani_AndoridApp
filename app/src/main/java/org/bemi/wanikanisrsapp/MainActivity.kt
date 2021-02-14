package org.bemi.wanikanisrsapp


import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.os.postDelayed
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.bemi.wanikanisrsapp.databinding.ActivityMainBinding


/**
 * Main Activity and entry point for the app.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var bottomNavView: BottomNavigationView
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)

        setUpViews()
    }

    // inflate toolbar menu items for top app bar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_app_bar, menu)

        return true
    }

    // on Click Listener for Toolbar items
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            // navigate to global settings Fragment
            R.id.settingsFragment -> {
                navController.navigate(R.id.settingsFragment)

                return true
            }
            //  Otherwise, do nothing and use the core event handling

            // when clauses require that all possible paths be accounted for explicitly,
            //  for instance both the true and false cases if the value is a Boolean,
            //  or an else to catch all unhandled cases.
            else -> super.onOptionsItemSelected(item)
        }

    }

    /**
     * Enables back button support. Simply navigates one element up on the stack.
     */
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    fun setUpViews() {
        // Get the navigation host fragment from this Activity
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        // Instantiate the navController using the NavHostFragment
        navController = navHostFragment.navController

        // get bottom navigation menu form Activity
        bottomNavView = findViewById(R.id.bottom_nav_view) as BottomNavigationView
        // setup bottom navigation with Navigation Controller
        NavigationUI.setupWithNavController(bottomNavView, navHostFragment.navController)

        // define single top-level destination
        // appBarConfiguration = AppBarConfiguration(navHostFragment.navController.graph)
        // define multiple top-level destinations
        appBarConfiguration = AppBarConfiguration(setOf(R.id.dashboardFragment, R.id.profileFragment))
        setupActionBarWithNavController(navHostFragment.navController, appBarConfiguration)

        //findViewById<Toolbar>(R.id.top_app_bar).setupWithNavController(navController, appBarConfiguration)
    }

    // set Bottom Navigation bar Visible
    fun showBottomNavigation()
    {
        bottomNavView.visibility = View.VISIBLE
    }

    // hide bottom navigation bar from view
    fun hideBottomNavigation()
    {
        bottomNavView.visibility = View.GONE
    }

    private var backPressedOnce = false

    // leave application after two back presses in short succession
    override fun onBackPressed() {
        if (navController.graph.startDestination == navController.currentDestination?.id)
        {
            if (backPressedOnce)
            {
                super.onBackPressed()
                return
            }

            // popup exit message after first back press
            backPressedOnce = true
            Toast.makeText(this, "Press BACK again to exit", Toast.LENGTH_SHORT).show()
            // reset first back press after two seconds without succeeding action
            Handler().postDelayed(2000) {
                backPressedOnce = false
            }
        }
        else {
            super.onBackPressed()
        }
    }

}