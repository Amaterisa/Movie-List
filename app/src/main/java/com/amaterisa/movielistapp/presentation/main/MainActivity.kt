package com.amaterisa.movielistapp.presentation.main

import android.os.Bundle
import android.view.Menu
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
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
import com.amaterisa.movielistapp.presentation.watchlist.WatchListFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    companion object {
        const val TAG = "MainActivity"
        const val HOME_FRAGMENT="HOME_FRAGMENT"
        const val POPULAR_MOVIES_FRAGMENT="POPULAR_MOVIES_FRAGMENT"
        const val WATCH_LIST_FRAGMENT="WATCH_LIST_FRAGMENT"
    }

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar.toolbarLayout)
        setupNavigation()

        if (savedInstanceState == null) {
            loadFragment(HomeFragment(), HOME_FRAGMENT)
        }
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
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.nav_home, R.id.nav_popular, R.id.nav_list))

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
                    loadFragment(HomeFragment(), HOME_FRAGMENT)
                    true
                }
                R.id.nav_popular -> {
                    loadFragment(PopularMoviesFragment(), POPULAR_MOVIES_FRAGMENT)
                    true
                }
                R.id.nav_list -> {
                    loadFragment(WatchListFragment(), WATCH_LIST_FRAGMENT)
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

    private fun loadFragment(fragment: Fragment, tag: String) {
        val fragmentManager = supportFragmentManager
        val existingFragment = fragmentManager.findFragmentByTag(tag)

        val transaction = fragmentManager.beginTransaction()

        if (existingFragment != null) {
            fragmentManager.fragments.forEach { transaction.hide(it) }
            transaction.show(existingFragment)
        } else {
            fragmentManager.fragments.forEach { transaction.hide(it) }
            transaction.add(R.id.nav_host_fragment, fragment, tag)
        }

        transaction.commit()
    }
}