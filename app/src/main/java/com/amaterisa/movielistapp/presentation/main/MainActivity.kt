package com.amaterisa.movielistapp.presentation.main

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.amaterisa.movielistapp.R
import com.amaterisa.movielistapp.databinding.ActivityMainBinding
import com.amaterisa.movielistapp.presentation.popularmovies.PopularMoviesFragment
import com.amaterisa.movielistapp.presentation.popularmovies.PopularMoviesViewModel
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
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        openFragment()
    }

    private fun openFragment() {
        // Create an instance of the fragment
        val fragment = PopularMoviesFragment()

        // Replace the current fragment with the new one
        supportFragmentManager.beginTransaction()
            .replace(R.id.main, fragment) // Replace with the correct container ID
            .addToBackStack(null) // Optional: Add to back stack for navigation
            .commit()
    }
}