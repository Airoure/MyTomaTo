package com.zjl.mytomato.ui.todolist

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.zjl.mytomato.*
import com.zjl.mytomato.adapter.TimeLineAdapter
import com.zjl.mytomato.databinding.FragmentTodoListBinding
import com.zjl.mytomato.view.ColorPickerDialog
import com.zjl.mytomato.view.SpacingDecoration

class TodoListFragment : BaseFragment<FragmentTodoListBinding, TodoListVm>() {
    private lateinit var timeLineadapter: TimeLineAdapter

    override fun initUi(): FragmentTodoListBinding {
        return FragmentTodoListBinding.inflate(layoutInflater).apply {
            timeLineadapter = TimeLineAdapter()

            calendar.setOnCalendarChangedListener { _, year, month, localDate, _ ->
                toolbar.title = "${year}年 ${month}月"
                vm.getFinishTodo(localDate.toString("yyyy年MM月dd日"))
            }
            toolbar.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.todo_list_today -> {
                        calendar.toToday()
                    }
                }
                true
            }
            rvTodoFinish.apply {
                adapter = timeLineadapter
                layoutManager = LinearLayoutManager(context)
                addItemDecoration(SpacingDecoration(10f, 0f, includeVEdge = true))
            }
        }
    }

    override fun onResume() {
        super.onResume()
        ui.calendar.toToday()
    }

    override fun init() {
        vm.finishTodoLiveData.observe(this, Observer {
            if (it.isEmpty()) {
                ui.layoutEmpty.setVisiable()
            } else {
                ui.layoutEmpty.setGone()
            }
            timeLineadapter.setFinishTodoEntityList(it)
        })
    }

    override fun initViewModel(): TodoListVm {
        return ViewModelProvider(this).get(TodoListVm::class.java)
    }
}