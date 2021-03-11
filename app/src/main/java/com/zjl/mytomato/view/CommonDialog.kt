package com.zjl.mytomato.view

import android.app.Dialog
import android.content.Context
import com.zjl.mytomato.R
import com.zjl.mytomato.databinding.DialogCommonBinding

class CommonDialog(
    context: Context,
    title: String = "提示",
    content: String,
    listener: DialogClickListener? = null
) : Dialog(context, R.style.BaseDialog) {

    private val ui: DialogCommonBinding

    init {
        ui = DialogCommonBinding.inflate(layoutInflater).apply {
            tvTitle.text = title
            tvContent.text = content
            tvConfirm.setOnClickListener {
                listener?.onClick()
                dismiss()
            }
            setCanceledOnTouchOutside(true)
            setContentView(root)
        }


    }

    fun interface DialogClickListener {
        fun onClick()
    }
}