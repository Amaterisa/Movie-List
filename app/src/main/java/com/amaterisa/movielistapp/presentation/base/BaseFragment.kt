package com.amaterisa.movielistapp.presentation.base

import androidx.fragment.app.Fragment
import com.amaterisa.movielistapp.presentation.main.FragmentConfig
import com.amaterisa.movielistapp.presentation.main.IMainActivity

abstract class BaseFragment: Fragment() {

    override fun onResume() {
        super.onResume()
        if (requireActivity() is IMainActivity) {
            val mainActivity = requireActivity() as IMainActivity
            mainActivity.setupNavigationLayout(fragmentType())
        }
    }

    abstract fun fragmentType(): FragmentConfig
}