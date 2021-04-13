package com.zjl.mytomato.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class LineGraph @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyle: Int = 0
) : View(
        context, attrs, defStyle
) {
    private var endX = 0f//滑动终点
    private val mLinePaint = Paint()
    private val mTextPaint = Paint()
    private val mXAxisPaint = Paint()
    private val mbrokenlinePaint = Paint()

    init {
        mLinePaint.apply {
            color = Color.BLACK
            strokeWidth = 1f
            isAntiAlias = true
        }
        mTextPaint.apply {
            textSize = 30f
            color = Color.BLACK
            strokeWidth = 5f
            isAntiAlias = true
            textAlign = Paint.Align.CENTER
        }
        mXAxisPaint.apply {
            textAlign = Paint.Align.CENTER
            textSize = 40f
            color = Color.BLACK
            strokeWidth = 5f
            isAntiAlias = true
        }
        mbrokenlinePaint.apply {
            isAntiAlias = true
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawLine(100f, 100f, 100f, (height - 100f), mLinePaint)
        canvas.drawLine(100f, height - 100f, width + endX, height - 100f, mLinePaint)

    }
}