package com.ekochkov.skillfactorykotlintest.utils

import android.app.Activity
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.AccelerateDecelerateInterpolator
import java.util.concurrent.Executors
import kotlin.math.hypot
import kotlin.math.roundToInt

object AnimationHelper {
    private const val menuItems = 5
    private const val menuItemCenterDivider = 2
    private const val firstItemСoef = 1

    fun performFragmentCircularRevealAnimation(view: View, activity: Activity, position: Int) {
        Executors.newSingleThreadExecutor().execute {
            while (true) {
                if (view.isAttachedToWindow) {
                    activity.runOnUiThread {
                        val itemCenter = view.width / (menuItems * menuItemCenterDivider)
                        val step = (itemCenter * menuItemCenterDivider) * (position - firstItemСoef) + itemCenter

                        val x: Int = step
                        val y: Int = view.y.roundToInt() + view.height
                        val startRadius = 0
                        val endRadius = hypot(view.width.toDouble(), view.height.toDouble())

                        ViewAnimationUtils.createCircularReveal(view, x, y, startRadius.toFloat(), endRadius.toFloat()).apply {
                            duration = 400
                            interpolator = AccelerateDecelerateInterpolator()
                            start()
                        }
                        view.visibility = View.VISIBLE
                    }
                    return@execute
                }
            }
        }
    }
}