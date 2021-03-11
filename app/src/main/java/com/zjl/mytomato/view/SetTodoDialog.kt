package com.zjl.mytomato.view

import android.app.Dialog
import android.content.Context
import android.widget.Toast
import com.zjl.mytomato.R
import com.zjl.mytomato.database.DatabaseManager
import com.zjl.mytomato.databinding.DialogSetTodoBinding
import com.zjl.mytomato.entity.TodoEntity
import java.util.*

class SetTodoDialog constructor(context: Context,listener: ButtonClickListener) :
    Dialog(context, R.style.BaseDialog) {
    private val ui: DialogSetTodoBinding

    init {
        ui = DialogSetTodoBinding.inflate(layoutInflater).apply {
            btnConfirm.setOnClickListener {
                val todoName = etTodoName.text.trim().toString()
                val hour = pickerHour.value
                val minute = pickerMinute.value
                if(todoName.isEmpty()){
                    CommonDialog(context,content = "待办名不能为空").show()
                }else if(hour == 0 && minute == 0){
                    CommonDialog(context,content = "时间不能设置为0").show()
                }else{
                    listener.onConfirmClick(TodoEntity(todoName,hour,minute,UUID.randomUUID().toString()))
                    dismiss()
                }
            }
            btnCancel.setOnClickListener {
                listener.onCancelClick()
                dismiss()
            }
        }
        setContentView(ui.root)
        setCanceledOnTouchOutside(false)
    }

    interface ButtonClickListener{
        fun onConfirmClick(todoEntity: TodoEntity)
        fun onCancelClick()
    }
}