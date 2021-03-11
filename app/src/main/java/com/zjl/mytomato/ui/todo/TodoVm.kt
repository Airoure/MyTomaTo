package com.zjl.mytomato.ui.todo

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zjl.mytomato.BaseViewModel
import com.zjl.mytomato.entity.TodoEntity

class TodoVm: BaseViewModel() {

    val messageLiveData = MutableLiveData<Int>()
    val todoLiveData = MutableLiveData<TodoEntity>()
    val firstLoadLiveData = MutableLiveData<MutableList<TodoEntity>>()

    private val repo by lazy { TodoRepo(viewModelScope) }

    fun addTodo(todoEntity: TodoEntity){
        repo.addTodo(todoEntity, messageLiveData,todoLiveData)
    }

    override fun load() {
        repo.getAllTodo(firstLoadLiveData)
    }

}