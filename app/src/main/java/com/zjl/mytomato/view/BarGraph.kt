package com.zjl.mytomato.view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.BounceInterpolator
import kotlin.random.Random

class BarGraph @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : View(
    context, attrs, defStyle
) {
    private val mLinePaint = Paint()
    private val mTextPaint = Paint()
    private val mXAxisPaint = Paint()
    private val mBarPaint = Paint()
    private var endX = 0f//滑动终点
    private var scrolledDist = 0f//已滑动距离
    private var oneTouchStart = 0f//一次滑动的起点
    private var oneTouchEnd = 0f//一次滑动的终点
    private var totalTime = 100L
    private var heightPercent = 0.0f//控制统计图出现动画的参数
    private var datas = mutableMapOf<String, Float>()

    private val mValuePath = Path()
    private val mXaxisPath = Path()
    private val timePath = Path()

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
        mBarPaint.apply {
            isAntiAlias = true
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawLine(100f, 100f, 100f, (height - 100f), mLinePaint)
        canvas.drawLine(100f, height - 100f, width + endX, height - 100f, mLinePaint)
        for (i in 0..10) {
            if (i % 2 == 0) {
                mValuePath.reset()
                mValuePath.moveTo(10f, (height - 100f) - (height - 200f) / 10 * i)
                mValuePath.lineTo(70f, (height - 100f) - (height - 200f) / 10 * i)
                canvas.drawLine(
                    90f,
                    (height - 100f) - (height - 200f) / 10 * i,
                    110f,
                    (height - 100f) - (height - 200f) / 10 * i,
                    mLinePaint
                )
                canvas.drawTextOnPath("${i * 10}%", mValuePath, 0f, 0f, mTextPaint)
            }
        }
        var i = 0
        val random = Random(1)
        if (!datas.isNullOrEmpty()) {
            for ((name, value) in datas) {
                var shortName = name
                if (name.length > 3) {
                    shortName = name.substring(0, 3) + ".."
                }
                //val path = Path()
                mBarPaint.color = Color.rgb(
                    random.nextInt(256),
                    random.nextInt(256),
                    random.nextInt(256)
                )
                mXaxisPath.reset()
                mXaxisPath.moveTo(130f + 160f * i, height - 50f)
                mXaxisPath.lineTo(260f + 160f * i, height - 50f)
                canvas.drawTextOnPath(shortName, mXaxisPath, 0f, 0f, mXAxisPaint)
                canvas.drawRect(
                    130f + 160f * i,
                    (100f) + (height - 200f) * (1 - value) + (height - 200f) * (value) * heightPercent,
                    260f + 160f * i,
                    height - 100f,
                    mBarPaint
                )
                timePath.reset()
                timePath.moveTo(130f + 160f * i, (95f) + (height - 200f) * (1 - value))
                timePath.lineTo(260f + 160f * i, (95f) + (height - 200f) * (1 - value))
                canvas.drawTextOnPath(
                    "${(value * totalTime).toInt()}分钟",
                    timePath,
                    0f,
                    0f,
                    mTextPaint
                )
                i++
            }
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

    fun setData(datas: MutableMap<String, Float>) {
        if (!datas.equals(this.datas)) {
            this.datas = datas
            scrollTo(0, 0)
            scrolledDist = 0f
            val valueAnimator = ValueAnimator.ofFloat(1.0f, 0.0f)
            valueAnimator.addUpdateListener {
                heightPercent = it.animatedValue as Float
                invalidate()
            }
            valueAnimator.interpolator = BounceInterpolator()
            valueAnimator.duration = 1000
            valueAnimator.start()
        }

    }

    fun setTotalTime(totalTime: Long) {
        this.totalTime = totalTime
    }

}