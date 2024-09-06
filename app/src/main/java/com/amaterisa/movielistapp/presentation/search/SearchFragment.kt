package com.amaterisa.movielistapp.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.amaterisa.movielistapp.R
import com.amaterisa.movielistapp.databinding.FragmentSearchBinding
import com.amaterisa.movielistapp.presentation.base.BaseFragment
import com.amaterisa.movielistapp.presentation.main.FragmentConfig
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment() {
    companion object {
        const val TAG = "SearchFragment"
    }

    private val viewModel: SearchViewModel by viewModels()

    private val binding: FragmentSearchBinding by lazy {
        FragmentSearchBinding.inflate(layoutInflater)
    }

    override fun fragmentType() = FragmentConfig.SEARCH

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }
}