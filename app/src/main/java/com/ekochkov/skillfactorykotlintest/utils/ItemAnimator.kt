package com.ekochkov.skillfactorykotlintest

import android.content.Context
import android.util.Log
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView


class ItemFilmAnimator(context: Context) : DefaultItemAnimator() {

    val animAdd = AnimationUtils.loadAnimation(context, R.anim.slide_from_left)

    override fun onAddStarting(item: RecyclerView.ViewHolder?) {
        Log.d("BMTH", "onAddStarting")
        item?.itemView?.startAnimation(animAdd)
    }

    override fun getAddDuration(): Long {
        return animAdd.duration
    }

    override fun animateMove(holder: RecyclerView.ViewHolder?, fromX: Int, fromY: Int, toX: Int, toY: Int): Boolean {
        Log.d("BMTH", "animateMove")
        return super.animateMove(holder, fromX, fromY, toX, toY)
    }
}