package com.ekochkov.skillfactorykotlintest.decoration

import android.content.Context
import android.graphics.*
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ekochkov.skillfactorykotlintest.R

class OffsetFilmItemDecoration: RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.set(20, 20, 20, 20)
    }

}