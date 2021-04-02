package com.zjl.mytomato.view

import android.app.Dialog
import android.content.Context
import android.util.Log
import com.zjl.mytomato.R
import com.zjl.mytomato.databinding.DialogTimedTaskBinding
import com.zjl.mytomato.entity.TodoEntity

class TimedTaskDialog(context: Context, todoEntities: List<TodoEntity>) : Dialog(context, R.style.BaseDialog) {

    private val ui: DialogTimedTaskBinding

    init {
        val todoEntityMap = mutableMapOf<String, TodoEntity>()
        for (item in todoEntities) {
            todoEntityMap[item.name] = item
        }
        ui = DialogTimedTaskBinding.inflate(layoutInflater).apply {
            pickerType.refreshByNewDisplayedValues(todoEntityMap.keys.toTypedArray())
        }
        setContentView(ui.root)

    }

}