package com.ekochkov.skillfactorykotlintest.customView

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import com.ekochkov.skillfactorykotlintest.R

class ProgressRingView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null): View(context, attributeSet) {

    companion object {
        private const val DEFAULT_PROGRESS_VALUE = 0
        private const val DEFAULT_RING_STROKE_VALUE = 10f
        private const val DEFAULT_IS_ANIMATE_VALUE = false
        private const val DEFAULT_STYLE_ATTR_VALUE = 0
        private const val DEFAULT_STYLE_RES_VALUE = 0
        private const val DEFAULT_TEXT_SIZE_VALUE = 60f
        private const val DEFAULT_ANIMATE_VALUE = 1000L
    }

    private var progressValue: Int
    private var ringStrokeValue: Float
    private var isAnimate = false

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
    private var progressRingAngle = zeroRingAngle
    private val progressRingAnimator = ValueAnimator.ofFloat().apply {
        duration = DEFAULT_ANIMATE_VALUE
        interpolator = AccelerateDecelerateInterpolator()
        addUpdateListener {
            progressRingAngle = it.animatedValue as Float
            invalidate()
        }
    }

    init {
        val attributes = context.theme.obtainStyledAttributes(attributeSet, R.styleable.ProgressRingView, DEFAULT_STYLE_ATTR_VALUE, DEFAULT_STYLE_RES_VALUE)

        try {
            progressValue = attributes.getInt(R.styleable.ProgressRingView_progressValue, DEFAULT_PROGRESS_VALUE)
            ringStrokeValue = attributes.getFloat(R.styleable.ProgressRingView_ringStrokeValue, DEFAULT_RING_STROKE_VALUE)
            isAnimate = attributes.getBoolean(R.styleable.ProgressRingView_isAnimateValue, DEFAULT_IS_ANIMATE_VALUE)
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
            color = getPaintColor(progressValue)
        }

        ratingTextPaint = Paint().apply {
            color = context.getColor(R.color.green_dark)
            style = Paint.Style.FILL_AND_STROKE
            textSize = DEFAULT_TEXT_SIZE_VALUE
            typeface = Typeface.DEFAULT_BOLD
            color = getPaintColor(progressValue)
        }

        backgroundPaint = Paint().apply {
            color = context.getColor(R.color.gray)
            style = Paint.Style.FILL_AND_STROKE
        }
    }

    private fun calculateEndRatingLineValue(value: Int) {
        endProgressRingAngle = value*progressRingAngleCoef
    }

    override fun onDraw(canvas: Canvas) {
        drawProgressRing(canvas)
        drawText(canvas)
    }

    private fun drawText(canvas: Canvas) {
        val message = String.format("%.1f", progressValue / 10f)
        //Получаем ширину и высоту текста, чтобы компенсировать их при отрисовке, чтобы текст был
        //точно в центре
        val widths = FloatArray(message.length)
        ratingTextPaint.getTextWidths(message, widths)
        ratingTextPaint.textSize = getTextSize()

        var advance = 0f
        for (width in widths) advance += width
        //Рисуем наш текст
        canvas.drawText(message, centerX - advance / 2, centerY  + advance / 4, ratingTextPaint)
    }

    private fun getTextSize(): Float {
        return if (width>height) {
            height / 3f
        } else {
            width / 3f
        }
    }

    private fun drawProgressRing(canvas: Canvas) {
        //Здесь мы можем регулировать размер нашего кольца
        val scale = radius * ringSizeCoef
        //Сохраняем канвас
        canvas.save()
        canvas.translate(centerX, centerY)
        ring.set(-scale, -scale, scale, scale)
        canvas.drawCircle(0f, 0f, radius, backgroundPaint)
        canvas.drawArc(ring, startProgressRingAngle, progressRingAngle, false, ratingRingPaint)
        //Восстанавливаем канвас
        canvas.restore()
    }

    fun runAnimate() {
        with(progressRingAnimator) {
            setFloatValues(0f, endProgressRingAngle)
            start()
        }
    }

    fun setProgress(value: Int) {
        progressValue = value
        calculateEndRatingLineValue(value)
        initPaint()
        if (isAnimate) {
            runAnimate()
        } else {
            progressRingAngle = endProgressRingAngle
            invalidate()
        }
    }

    private fun getPaintColor(progress: Int): Int = when(progress) {
        in 0 .. 25 -> context.getColor(R.color.red_1)
        in 26 .. 50 -> context.getColor(R.color.orange_light)
        in 51 .. 75 -> context.getColor(R.color.yellow)
        else -> context.getColor(R.color.green)
    }
}