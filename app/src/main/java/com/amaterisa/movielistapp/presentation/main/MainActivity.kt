package com.amaterisa.movielistapp.presentation.main

import android.os.Bundle
import android.view.Menu
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.amaterisa.movielistapp.R
import com.amaterisa.movielistapp.databinding.ActivityMainBinding
import com.amaterisa.movielistapp.presentation.home.HomeFragment
import com.amaterisa.movielistapp.presentation.moviedetails.MovieDetailsFragment
import com.amaterisa.movielistapp.presentation.popularmovies.PopularMoviesFragment
import com.amaterisa.movielistapp.presentation.search.SearchFragment
import com.amaterisa.movielistapp.presentation.watchlist.WatchListFragment
import com.amaterisa.movielistapp.utils.ViewUtils.toVisibility
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlin.system.exitProcess

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), IMainActivity {
    companion object {
        const val TAG = "MainActivity"
    }

    private val viewModel: MainViewModel by viewModels<MainViewModel>()

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

        if (savedInstanceState == null) {
            //loadFragment(FragmentConfig.HOME)
        }

//        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
//            override fun handleOnBackPressed() {
//                //onBack()
//            }
//        })
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

        bottomNav?.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    navController?.navigate(R.id.nav_home)
                    //loadFragment(FragmentConfig.HOME)
                    true
                }

                R.id.nav_popular -> {
                    navController?.navigate(R.id.nav_popular)
                    //loadFragment(FragmentConfig.POPULAR_MOVIES)
                    true
                }

                R.id.nav_list -> {
                    navController?.navigate(R.id.nav_list)
                    //loadFragment(FragmentConfig.WATCH_LIST)
                    true
                }

                else -> false
            }
        }

        binding.toolbar.toolbarLayout.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_search -> {
                    goToSearch()
                    //loadFragment(FragmentConfig.SEARCH, true)
                    true
                }

                android.R.id.home -> {
                    onBackPressedDispatcher.onBackPressed()
                    true
                }

                else -> false
            }
        }
    }

    private fun loadFragment(fragmentConfig: FragmentConfig, addToBackStack: Boolean = false) {
        val fragmentManager = supportFragmentManager
        val existingFragment = fragmentManager.findFragmentByTag(fragmentConfig.fragmentName)
        val transaction = fragmentManager.beginTransaction()

        fragmentManager.fragments.forEach {
            transaction.setMaxLifecycle(it, Lifecycle.State.STARTED)
            transaction.hide(it)
        }

        if (existingFragment != null) {
            transaction.show(existingFragment)
            transaction.setMaxLifecycle(existingFragment, Lifecycle.State.RESUMED)
        } else {
            transaction.add(
                R.id.nav_host_fragment,
                getFragment(fragmentConfig),
                fragmentConfig.fragmentName
            )
        }

        if (addToBackStack) {
            viewModel.backStack.add(0, viewModel.currentFragment)
        }

        transaction.commit()
        viewModel.currentFragment = fragmentConfig
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
                FragmentConfig.HOME, FragmentConfig.POPULAR_MOVIES, FragmentConfig.WATCH_LIST -> {
                    showBackButton(false)
                    showMenuOptions(true)
                    showBottomNav(true)
                }

                FragmentConfig.SEARCH -> {
                    showBackButton(true)
                    showMenuOptions(false)
                    showBottomNav(false)
                }

                FragmentConfig.MOVIE_DETAILS -> {
                    showBackButton(true)
                    showMenuOptions(false)
                    showBottomNav(true)
                }
            }
        }
    }

    override fun goToMovieDetails(id: Long) {
        val bundle = bundleOf("movieId" to id)
        navController?.navigate(R.id.nav_movie_details, bundle)
    }

    fun goToSearch() {
        navController?.navigate(R.id.nav_search)
    }

    private fun onBack() {
        if (viewModel.backStack.isNotEmpty()) {
            val fragment = viewModel.backStack.removeAt(0)
            loadFragment(fragment)
        } else {
            if (viewModel.currentFragment != FragmentConfig.HOME) {
                bottomNav?.selectedItemId = R.id.nav_home
            } else {
                exitProcess(0)
            }
        }
    }

    private fun getFragment(fragmentConfig: FragmentConfig): Fragment {
        return when (fragmentConfig) {
            FragmentConfig.HOME -> HomeFragment()
            FragmentConfig.POPULAR_MOVIES -> PopularMoviesFragment()
            FragmentConfig.WATCH_LIST -> WatchListFragment()
            FragmentConfig.SEARCH -> SearchFragment()
            FragmentConfig.MOVIE_DETAILS -> MovieDetailsFragment()
        }
    }
}