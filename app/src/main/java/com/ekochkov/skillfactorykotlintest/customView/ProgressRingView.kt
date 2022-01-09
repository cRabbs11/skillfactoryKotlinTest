package com.ekochkov.skillfactorykotlintest.customView

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.ekochkov.skillfactorykotlintest.R

class ProgressRingView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null): View(context, attributeSet) {

    private var progressValue: Int
    private var ringStrokeValue: Float

    init {
        val attributes = context.theme.obtainStyledAttributes(attributeSet, R.styleable.FilmRatingView, 0, 0)

        try {
            progressValue = attributes.getInt(R.styleable.ProgressRingView_progressValue, 0)
            ringStrokeValue = attributes.getFloat(R.styleable.ProgressRingView_ringStrokeValue, 10f)
        } finally {
            attributes.recycle()
        }
    }
}