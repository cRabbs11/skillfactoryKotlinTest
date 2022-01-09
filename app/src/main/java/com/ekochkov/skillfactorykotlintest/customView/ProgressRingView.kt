package com.ekochkov.skillfactorykotlintest.customView

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import com.ekochkov.skillfactorykotlintest.R

class ProgressRingView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null): View(context, attributeSet) {

    private var progressValue: Int
    private var ringStrokeValue: Float

    private lateinit var ratingRingPaint : Paint
    private lateinit var ratingTextPaint : Paint
    private lateinit var backgroundPaint : Paint

    private var ring = RectF()
    private val ringSizeCoef = 0.8f

    private var radius = 0f
    private var centerX: Float = 0f
    private var centerY: Float = 0f

    private val progressRingAngleCoef = 3.6f
    private val zeroRingAngle = 0f
    private val startProgressRingAngle = -90f
    private var endProgressRingAngle = zeroRingAngle

    init {
        val attributes = context.theme.obtainStyledAttributes(attributeSet, R.styleable.FilmRatingView, 0, 0)

        try {
            progressValue = attributes.getInt(R.styleable.ProgressRingView_progressValue, 0)
            ringStrokeValue = attributes.getFloat(R.styleable.ProgressRingView_ringStrokeValue, 10f)
        } finally {
            attributes.recycle()
        }

        initPaint()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        //Считаем полный размер с паддингами
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec) + paddingLeft + paddingRight

        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec) + paddingBottom + paddingTop

        val chosenWidth = chooseDimension(widthMode, widthSize)
        val chosenHeight = chooseDimension(heightMode, heightSize)

        val minSide = Math.min(chosenWidth, chosenHeight)
        centerX = minSide.div(2f)
        centerY = minSide.div(2f)

        setMeasuredDimension(minSide, minSide)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        if (width<height) {
            radius = width / 2f
        } else { radius = height / 2f}
    }

    private fun chooseDimension(mode: Int, size: Int) =
            when (mode) {
                MeasureSpec.AT_MOST, MeasureSpec.EXACTLY -> size
                else -> 300
            }

    private fun initPaint() {
        ratingRingPaint = Paint().apply {
            color = context.getColor(R.color.green_dark)
            style = Paint.Style.STROKE
            strokeWidth = ringStrokeValue
            isAntiAlias = true
        }

        ratingTextPaint = Paint().apply {
            color = context.getColor(R.color.green_dark)
            style = Paint.Style.FILL_AND_STROKE
            textSize = 60f
            typeface = Typeface.DEFAULT_BOLD
        }

        backgroundPaint = Paint().apply {
            color = context.getColor(R.color.gray)
            style = Paint.Style.FILL_AND_STROKE
        }
    }

    private fun calculateEndRatingLineValue(value: Int) {
        endProgressRingAngle = value*progressRingAngleCoef
    }

    private fun drawProgressRing(canvas: Canvas) {
        //Здесь мы можем регулировать размер нашего кольца
        val scale = radius * ringSizeCoef
        //Сохраняем канвас
        canvas.save()
        canvas.translate(centerX, centerY)
        ring.set(-scale, -scale, scale, scale)
        canvas.drawCircle(0f, 0f, radius, backgroundPaint)
        canvas.drawArc(ring, startProgressRingAngle, endProgressRingAngle, false, ratingRingPaint)
        //Восстанавливаем канвас
        canvas.restore()
    }
}