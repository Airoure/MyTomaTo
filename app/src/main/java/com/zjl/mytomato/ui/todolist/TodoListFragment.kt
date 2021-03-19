package com.zjl.mytomato.ui.todolist

import androidx.lifecycle.ViewModelProvider
import com.zjl.mytomato.BaseFragment
import com.zjl.mytomato.databinding.FragmentTodoListBinding
import com.zjl.mytomato.ui.todo.TodoVm

class TodoListFragment : BaseFragment<FragmentTodoListBinding, TodoVm>() {
    override fun initUi(): FragmentTodoListBinding {
        return FragmentTodoListBinding.inflate(layoutInflater).apply {

        }
    }

    override fun initViewModel(): TodoVm {
        return ViewModelProvider(this).get(TodoVm::class.java)
    }
}