package com.zjl.mytomato.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.zjl.mytomato.databinding.ViewDayWeekRadioBinding

class DayOrWeekRadio @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyle: Int = 0
) : FrameLayout(
        context, attrs, defStyle
) {
    private val ui: ViewDayWeekRadioBinding
    private var listener: OnRadioCheckedListener? = null

    init {
        ui = ViewDayWeekRadioBinding.inflate(
                LayoutInflater.from(context),
                this,
                true
        ).apply {
            btnWeek.setOnClickListener {
                listener?.onRadioChecked(0)
            }
            btnDay.setOnClickListener {
                listener?.onRadioChecked(1)
            }
        }
    }

    fun setOnRadioCheckedListener(listener: OnRadioCheckedListener){
        this.listener = listener
    }

    fun interface OnRadioCheckedListener {
        fun onRadioChecked(index: Int)
    }
}