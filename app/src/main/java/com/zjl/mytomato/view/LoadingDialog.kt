package com.zjl.mytomato.view

import android.app.Dialog
import android.content.Context
import com.zjl.mytomato.R
import com.zjl.mytomato.databinding.DialogLoadingBinding

class LoadingDialog(context: Context) :Dialog(context, R.style.BaseDialog) {
    init {
        setCancelable(false)
        setContentView(DialogLoadingBinding.inflate(layoutInflater).root)
    }
}