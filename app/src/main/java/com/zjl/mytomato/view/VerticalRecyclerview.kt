package com.zjl.mytomato.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewConfiguration
import androidx.recyclerview.widget.RecyclerView

class VerticalRecyclerview @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(
    context,
    attributeSet,
    defStyleAttr
) {
    private var initX: Float = 0f
    override fun onInterceptTouchEvent(e: MotionEvent?): Boolean {
        if (e != null) {
            when (e.action) {
                MotionEvent.ACTION_DOWN -> {
                    initX = e.rawX
                }
                MotionEvent.ACTION_MOVE -> {
                    if (e.rawX - initX >= ViewConfiguration.get(context).scaledTouchSlop) {
                        return false
                    }
                }
                MotionEvent.ACTION_UP -> {
                    if (e.rawX - initX >= ViewConfiguration.get(context).scaledTouchSlop) {
                        return false
                    }
                }
            }
        }
        return super.onInterceptTouchEvent(e)
    }
}