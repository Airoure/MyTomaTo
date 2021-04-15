package com.zjl.mytomato.view

import android.app.Dialog
import android.content.Context
import com.zjl.mytomato.R
import com.zjl.mytomato.databinding.DialogTimedTaskBinding
import com.zjl.mytomato.entity.TimedTaskEntity
import com.zjl.mytomato.entity.TodoEntity

class TimedTaskDialog(
    context: Context,
    todoEntities: List<TodoEntity>,
    onClick: (TimedTaskEntity) -> Unit
) : Dialog(context, R.style.BaseDialog) {

    private val ui: DialogTimedTaskBinding
    private var isMonday: Boolean = false
    private var isTuesday: Boolean = false
    private var isWednesday: Boolean = false
    private var isThursday: Boolean = false
    private var isFriday: Boolean = false
    private var isSaturday: Boolean = false
    private var isSunday: Boolean = false

    init {
        val todoEntityMap = mutableMapOf<String, TodoEntity>()
        for (item in todoEntities) {
            todoEntityMap[item.name] = item
        }
        ui = DialogTimedTaskBinding.inflate(layoutInflater).apply {
            if (todoEntityMap.isNotEmpty()) {
                pickerType.refreshByNewDisplayedValues(todoEntityMap.keys.toTypedArray())
            } else {
                pickerType.refreshByNewDisplayedValues(arrayOf("请先添加待办"))
                btnConfirm.isEnabled = false
                viewMonday.isChecked = true
            }
            btnConfirm.setOnClickListener {
                isMonday = viewMonday.isChecked
                isTuesday = viewTuesday.isChecked
                isWednesday = viewWednesday.isChecked
                isThursday = viewThursday.isChecked
                isFriday = viewFriday.isChecked
                isSaturday = viewSaturday.isChecked
                isSunday = viewSunday.isChecked
                if (!isMonday and !isTuesday and !isWednesday and !isThursday and !isFriday and !isSaturday and !isSunday) {
                    CommonDialog(context, content = "请选择至少一个日期", touchOutCamcel = true).show()
                    return@setOnClickListener
                }
                val todoEntity = todoEntityMap[pickerType.displayedValues[pickerType.value]]!!
                with(todoEntity) {
                    onClick.invoke(
                        TimedTaskEntity(
                            name,
                            hour,
                            minute,
                            second,
                            imageUrl,
                            pickerTimeHour.value,
                            pickerTimeMinute.value,
                            enable = false,
                            isMonday,
                            isTuesday,
                            isWednesday,
                            isThursday,
                            isFriday,
                            isSaturday,
                            isSunday
                        )
                    )
                }
                dismiss()
            }
        }
        setContentView(ui.root)

    }

}