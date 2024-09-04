package com.amaterisa.movielistapp.presentation.adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class LinearItemDecoration(private val space: Int, private val isVertical: Boolean) :
    RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        if (isVertical) {
            outRect.bottom = space
        } else {
            outRect.left = space / 2
            outRect.right = space / 2
        }
    }
}