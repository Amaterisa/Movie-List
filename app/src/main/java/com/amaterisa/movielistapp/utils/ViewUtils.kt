package com.amaterisa.movielistapp.utils

import android.view.View

object ViewUtils {
    fun View.toVisibility(visible: Boolean) {
        if (visible) {
            visibility = View.VISIBLE
        } else {
            visibility = View.GONE
        }
    }
}