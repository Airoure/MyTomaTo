package com.zjl.mytomato.view

import android.app.Dialog
import android.content.Context
import com.zjl.mytomato.R
import com.zjl.mytomato.databinding.DialogColorPickerBinding

class ColorPickerDialog(context: Context, confirmClick: (color: Int) -> Unit) : Dialog(
    context,
    R.style.BaseDialog
) {
    private val ui: DialogColorPickerBinding

    init {
        ui = DialogColorPickerBinding.inflate(layoutInflater).apply {
            colorPicker.setOnColorChangedListener { color ->
                btnConfirm.setBackgroundColor(color)
            }
            btnConfirm.setOnClickListener {
                confirmClick(colorPicker.color)
                dismiss()
            }
        }
        setContentView(ui.root)
    }
}