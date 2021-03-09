package com.zjl.mytomato.ui.todolist

import com.zjl.mytomato.BaseFragment
import com.zjl.mytomato.databinding.FragmentTodoListBinding

class TodoListFragment :BaseFragment<FragmentTodoListBinding>() {
    override fun initUi(): FragmentTodoListBinding {
        return FragmentTodoListBinding.inflate(layoutInflater).apply {

        }
    }
}