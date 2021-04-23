package com.zjl.mytomato.ui.me

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.zjl.mytomato.BaseViewModel
import com.zjl.mytomato.entity.TodoEntity
import com.zjl.mytomato.ui.todo.TodoRepo

class MeViewModel : BaseViewModel() {
    private val repo by lazy { TodoRepo(viewModelScope) }
    val localTodoList = MutableLiveData<MutableSet<TodoEntity>>()
    val messageLiveData = MutableLiveData<Int>()
    val todoLiveData = MutableLiveData<TodoEntity>()
    fun getAllTodo() {
        repo.getAllTodoInset(localTodoList)
    }

    fun addTodo(todoEntity: TodoEntity) {
        repo.addTodo(todoEntity,messageLiveData,todoLiveData)
    }
}