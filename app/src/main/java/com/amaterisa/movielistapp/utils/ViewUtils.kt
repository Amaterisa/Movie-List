package com.amaterisa.movielistapp.utils

import android.view.View

object ViewUtils {
    fun View.toVisibility(visible: Boolean) {
        visibility = if (visible) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }
}