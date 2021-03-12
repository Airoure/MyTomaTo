package com.zjl.mytomato.view

import android.app.Dialog
import android.content.Context
import com.bumptech.glide.Glide
import com.zjl.mytomato.R
import com.zjl.mytomato.databinding.DetailTodoBinding

class TodoDetailDialog(
    context: Context,
) :Dialog(context, R.style.BaseDialog) {

    private val ui: DetailTodoBinding

    init {
        ui = DetailTodoBinding.inflate(layoutInflater)
        ui.apply {
            ivBack.let {
                Glide.with(context).load("").into(it)
            }
        }
    }
}