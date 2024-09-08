package com.amaterisa.movielistapp.presentation.main

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.amaterisa.movielistapp.R
import com.amaterisa.movielistapp.databinding.ActivityMainBinding
import com.amaterisa.movielistapp.domain.model.Movie
import com.amaterisa.movielistapp.presentation.utils.ViewUtils.toVisibility
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), IMainActivity {
    companion object {
        const val TAG = "MainActivity"
    }

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private var navController: NavController? = null
    private var bottomNav: BottomNavigationView? = null

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

    private fun setupNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        val appBarConfiguration =
            AppBarConfiguration(
                setOf(
                    R.id.nav_home,
                    R.id.nav_popular,
                    R.id.nav_list
                )
            )

        bottomNav = binding.bottomNavigation

        navController?.let {
            binding.toolbar.toolbarLayout.setupWithNavController(it, appBarConfiguration)
            NavigationUI.setupWithNavController(binding.bottomNavigation, it)
            setupActionBarWithNavController(it, appBarConfiguration)
            bottomNav?.setupWithNavController(it)
        }

        binding.toolbar.toolbarLayout.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_search -> {
                    goToSearch()
                    true
                }

                else -> false
            }
        }
    }

    private fun showBackButton(show: Boolean) {
        supportActionBar?.setHomeButtonEnabled(show)
        supportActionBar?.setDisplayHomeAsUpEnabled(show)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
        supportActionBar?.setDisplayHomeAsUpEnabled(show)
    }

    private fun showMenuOptions(show: Boolean) {
        val searchItem = binding.toolbar.toolbarLayout.menu?.findItem(R.id.nav_search)
        searchItem?.isVisible = show
    }

    private fun showBottomNav(show: Boolean) {
        binding.bottomNavigation.toVisibility(show)
    }

    override fun setupNavigationLayout(fragmentConfig: FragmentConfig) {
        binding.run {
            toolbar.toolbarLayout.title = fragmentConfig.fragmentName
            when (fragmentConfig) {
                FragmentConfig.HOME,
                FragmentConfig.POPULAR_MOVIES,
                FragmentConfig.WATCH_LIST -> {
                    showBackButton(false)
                    showMenuOptions(true)
                    showBottomNav(true)
                }

                FragmentConfig.MOVIE_DETAILS,
                FragmentConfig.SEARCH -> {
                    showBackButton(true)
                    showMenuOptions(false)
                    showBottomNav(false)
                }
            }
        }
    }

    override fun goToMovieDetails(movie: Movie) {
        navController?.navigate(R.id.nav_movie_details)
    }

    fun goToSearch() {
        navController?.navigate(R.id.nav_search)
    }
}