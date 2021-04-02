package com.zjl.mytomato.ui.mine

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.zjl.mytomato.BaseViewModel
import com.zjl.mytomato.entity.TodoEntity
import com.zjl.mytomato.ui.todo.TodoRepo

class MineVm : BaseViewModel() {

    private val repo by lazy { MineRepo(viewModelScope) }

    val todoEntities = MutableLiveData<List<TodoEntity>>()
    override fun load() {
        repo.getAllTodoName(todoEntities)
    }
}