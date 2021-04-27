package com.zjl.mytomato.view

import android.app.Dialog
import android.content.Context
import com.zjl.mytomato.R
import com.zjl.mytomato.databinding.DialogSetMaxExitTimeBinding
import com.zjl.mytomato.util.SpUtil

class SetMaxExitTimeDialog(context: Context) : Dialog(
    context, R.style.BaseDialog
) {
    private val ui: DialogSetMaxExitTimeBinding

    init {
        ui = DialogSetMaxExitTimeBinding.inflate(layoutInflater).apply {
            pickerTimes.maxValue = 11
            val maxTime = SpUtil.getMaxExitTime()
            if (maxTime == Int.MAX_VALUE) {
                pickerTimes.value = 11
            } else {
                pickerTimes.value = maxTime
            }
            btnConfirm.setOnClickListener {
                if (pickerTimes.value == 11) {
                    SpUtil.setMaxExitTime(Int.MAX_VALUE)
                } else {
                    SpUtil.setMaxExitTime(pickerTimes.value)
                }
                dismiss()
            }
        }

        setContentView(ui.root)
    }
}