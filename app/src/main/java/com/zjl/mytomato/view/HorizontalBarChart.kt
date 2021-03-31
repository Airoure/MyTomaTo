package com.zjl.mytomato.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.FrameLayout

class HorizontalBarChart @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null,
        defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val paint = Paint()
        paint.color = Color.BLACK
        paint.strokeWidth = 5f
        canvas!!.drawLine(0f, 0f, 100f, 100f, paint)
    }
}