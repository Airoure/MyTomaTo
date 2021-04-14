package com.zjl.mytomato.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
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
    private val mPointPaint = Paint()
    private val mbrokenlinePaint = Paint()

    private var datas = mapOf<String, Int>()

    private var scrolledDist = 0f//已滑动距离
    private var oneTouchStart = 0f//一次滑动的起点
    private var oneTouchEnd = 0f//一次滑动的终点

    private val brokenlinePath = Path()
    private val datePath = Path()
    private val timePath = Path()

    init {
        //setData(mutableMapOf("4-13" to 1236, "4-14" to 600, "4-15" to 422, "4-16" to 513, "4-17" to 678, "4-18" to 521, "4-19" to 21))
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
        mPointPaint.apply {
            style = Paint.Style.STROKE
            strokeWidth = 4f
            isAntiAlias = true
        }
        mbrokenlinePaint.apply {
            isAntiAlias = true
            shader = LinearGradient(130f, height - 50f, width.toFloat(), height.toFloat(), intArrayOf(Color.RED, Color.GREEN), floatArrayOf(0f, height.toFloat()), Shader.TileMode.REPEAT)
            style = Paint.Style.STROKE
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawLine(100f, 100f, 100f, (height - 100f), mLinePaint)
        canvas.drawLine(100f, height - 100f, width + endX, height - 100f, mLinePaint)
        canvas.drawLine(100f, height - 100f, (width*3).toFloat(), height - 100f, mLinePaint)
        var i = 0
        if (!datas.isNullOrEmpty()) {
            val maxValue = datas.maxByOrNull { it.value }
            for ((date, value) in datas) {
                datePath.reset()
                datePath.moveTo(130f + 160f * i, height - 50f)
                datePath.lineTo(260f + 160f * i, height - 50f)
                canvas.drawTextOnPath(date, datePath, 0f, 0f, mXAxisPaint)
                canvas.drawCircle((390 + 320f * i) / 2, (100f) + (height - 200f) * (1 - value / maxValue!!.value.toFloat()), 10f, mPointPaint)
                if (i == 0) {
                    brokenlinePath.moveTo((390 + 320f * i) / 2, (100f) + (height - 200f) * (1 - value / maxValue.value.toFloat()))
                } else {
                    brokenlinePath.lineTo((390 + 320f * i) / 2, (100f) + (height - 200f) * (1 - value / maxValue.value.toFloat()))
                }
                timePath.reset()
                timePath.moveTo(130f + 160f * i, (50f) + (height - 200f) * (1 - value / maxValue!!.value.toFloat()))
                timePath.lineTo(260f + 160f * i, (50f) + (height - 200f) * (1 - value / maxValue!!.value.toFloat()))
                canvas.drawTextOnPath("${value}分钟", timePath, 0f, 0f, mTextPaint)
                i++
            }
            canvas.drawPath(brokenlinePath, mbrokenlinePaint)
            brokenlinePath.reset()
        }
        endX = 160f + 160f * (datas.size) - width
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                oneTouchStart = event.x
            }
            MotionEvent.ACTION_MOVE, MotionEvent.ACTION_UP -> {
                oneTouchEnd = event.x
                val offset = (oneTouchEnd - oneTouchStart) / 2
                if (scrolledDist - offset >= 0 && scrolledDist - offset <= endX) {
                    scrolledDist -= offset
                    scrollBy(-offset.toInt(), 0)
                } else if (scrolledDist - offset < 0) {
                    scrolledDist = 0f
                    scrollTo(0, 0)
                } else if (scrolledDist - offset > endX) {
                    scrolledDist = endX
                    scrollTo(endX.toInt(), 0)
                }

            }
        }
        return true
    }

    fun setData(datas: Map<String, Int>) {
        if (!this.datas.equals(datas)) {
            this.datas = datas
            invalidate()
        }
    }
}