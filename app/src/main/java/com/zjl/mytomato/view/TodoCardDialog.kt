package com.zjl.mytomato.view

import android.animation.ObjectAnimator
import android.app.Dialog
import android.content.Context
import com.bumptech.glide.Glide
import com.zjl.mytomato.R
import com.zjl.mytomato.common.Constant.BASE_PIC_URL
import com.zjl.mytomato.databinding.DialogTodoCardBinding
import com.zjl.mytomato.entity.TodoEntity
import java.util.*

class TodoCardDialog constructor(
        context: Context,
        todoEntity: TodoEntity,
        save: (TodoEntity) -> Unit
) :
        Dialog(context, R.style.BaseDialog) {
    private val ui: DialogTodoCardBinding

    init {
        ui = DialogTodoCardBinding.inflate(layoutInflater).apply {
            etTodoName.setText(todoEntity.name)
            btnCancel.setOnClickListener {
                dismiss()
            }
            ivBack.apply {
                Glide.with(context)
                        .load("${BASE_PIC_URL}${todoEntity.imageUrl}")
                        .placeholder(resources.getDrawable(R.color.black))
                        .into(this)
            }
            pickerHour.maxValue = 9
            pickerMinute.maxValue = 59
            pickerHour.value = todoEntity.hour
            pickerMinute.value = todoEntity.minute
            var url = todoEntity.imageUrl
            btnConfirm.setOnClickListener {
                val todoName = etTodoName.text.trim().toString()
                val hour = pickerHour.value
                val minute = pickerMinute.value
                if (todoName.isEmpty()) {
                    CommonDialog(context, content = "待办名不能为空").show()
                } else if (hour == 0 && minute == 0) {
                    CommonDialog(context, content = "时间不能设置为0").show()
                } else {
                    val todoEntity = TodoEntity(todoName, hour, minute, imageUrl = url)
                    save.invoke(todoEntity)
                    dismiss()
                }
            }
            ivRefresh.apply {
                setOnClickListener {
                    ObjectAnimator.ofFloat(it, "rotation", 0f, 360f).setDuration(500).start()
                    url = UUID.randomUUID().toString()
                    Glide.with(context)
                            .load("https://source.unsplash.com/1600x900/?nature/${url}")
                            .placeholder(resources.getDrawable(R.color.black))
                            .into(ivBack)
                }
            }
        }
        setContentView(ui.root)
        setCanceledOnTouchOutside(true)
    }

}