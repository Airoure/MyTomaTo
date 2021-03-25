package com.zjl.mytomato.ui.todolist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.zjl.mytomato.BaseViewModel
import com.zjl.mytomato.entity.FinishTodoEntity

class TodoListVm : BaseViewModel() {
    private val repo by lazy { TodoListRepo(viewModelScope) }
    val finishTodoLiveData = MutableLiveData<List<FinishTodoEntity>>()

    fun getFinishTodo(date: String) {
        repo.getFinishTodoByDate(date, finishTodoLiveData)
    }

}