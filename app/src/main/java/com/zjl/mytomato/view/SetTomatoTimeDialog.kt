package com.zjl.mytomato.view

import android.app.Dialog
import android.content.Context
import com.zjl.mytomato.R
import com.zjl.mytomato.databinding.DialogSetTomatoTimeBinding
import com.zjl.mytomato.util.SpUtil

class SetTomatoTimeDialog(context: Context) : Dialog(
        context, R.style.BaseDialog
) {
    private val ui: DialogSetTomatoTimeBinding

    init {
        ui = DialogSetTomatoTimeBinding.inflate(layoutInflater).apply {
            pickerRestTime.maxValue = 58
            pickerWorkTime.maxValue = 58
            pickerWorkTime.value = (SpUtil.getWorkTime() / 60 / 1000).toInt() - 1
            pickerRestTime.value = (SpUtil.getRestTime() / 60 / 1000).toInt() - 1
            btnConfirm.setOnClickListener {
                SpUtil.setWorkTime((pickerWorkTime.value + 1).toLong())
                SpUtil.setRestTime((pickerRestTime.value + 1).toLong())
                dismiss()
            }
        }
        setContentView(ui.root)
    }
}