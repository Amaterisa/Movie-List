package com.amaterisa.movielistapp.presentation.main

import android.os.Bundle
import android.view.Menu
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.amaterisa.movielistapp.R
import com.amaterisa.movielistapp.databinding.ActivityMainBinding
import com.amaterisa.movielistapp.presentation.home.HomeFragment
import com.amaterisa.movielistapp.presentation.popularmovies.PopularMoviesFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    companion object {
        const val TAG = "MainActivity"
    }

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar.toolbarLayout)
        setupNavigation()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun setupNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.nav_home, R.id.nav_popular))

        binding.toolbar.toolbarLayout.setupWithNavController(navController, appBarConfiguration)
        setupActionBarWithNavController(navController, appBarConfiguration)

        val bottomNav = binding.bottomNavigation
        bottomNav.setupWithNavController(navController)
        bottomNavItemChangeListener(bottomNav, navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.toolbar.toolbarLayout.title = destination.label
        }

        bottomNav.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.nav_host_fragment, HomeFragment())
                        .commit()
                    true
                }

                R.id.nav_popular -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.nav_host_fragment, PopularMoviesFragment())
                        .commit()
                    true
                }

                else -> false
            }
        }
    }

    private fun bottomNavItemChangeListener(
        navView: BottomNavigationView,
        navController: NavController
    ) {
        navView.setOnItemSelectedListener { item ->
            if (item.itemId != navView.selectedItemId) {
                navController.popBackStack(item.itemId, inclusive = true, saveState = false)
                navController.navigate(item.itemId)
            }
            true
        }
    }
}