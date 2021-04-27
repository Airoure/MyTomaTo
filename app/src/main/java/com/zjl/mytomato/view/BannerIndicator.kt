package com.zjl.mytomato.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.zjl.mytomato.util.dp

class BannerIndicator @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : View(context, attributeSet, defStyle) {
    var mCurrentPosition = 0
        set(value) {
            field = value
            invalidate()
        }
    var mCellCount = 5
        set(value) {
            field = value
            invalidate()
        }
    private val mCellRadius = 3.dp
    private val mPaint: Paint = Paint()
    private val mCellMargin = 4.dp
    private val mIndicatorColor = Color.parseColor("#000000")

    init {
        mPaint.isAntiAlias = true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var width: Int =
            paddingLeft + paddingRight + mCellRadius * 2 * mCellCount + mCellMargin * (mCellCount - 1)
        var height = (paddingTop + paddingBottom + mCellRadius * 2).toInt()
        width = resolveSize(width, widthMeasureSpec)
        height = resolveSize(height, heightMeasureSpec)
        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        for (i in 0 until mCellCount) {
            if (i == mCurrentPosition) {
                mPaint.color = mIndicatorColor;
            } else {
                mPaint.color = Color.GRAY;
            }
            val left = paddingLeft + i * mCellRadius * 2 + mCellMargin * i
            canvas!!.drawCircle(
                (left + mCellRadius).toFloat(),
                (height / 2).toFloat(),
                mCellRadius.toFloat(),
                mPaint
            )
        }
    }
}