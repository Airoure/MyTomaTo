package com.zjl.mytomato.ui.todolist

import androidx.lifecycle.MutableLiveData
import com.zjl.mytomato.BaseRepo
import com.zjl.mytomato.database.DatabaseManager
import com.zjl.mytomato.entity.FinishTodoEntity
import kotlinx.coroutines.CoroutineScope

class TodoListRepo(coroutineScope: CoroutineScope) : BaseRepo(coroutineScope) {

    fun addFinishTodo(
        finishTodoEntity: FinishTodoEntity
    ) {
        launch(
            block = {
                DatabaseManager.get().insertFinishTodoEntity(finishTodoEntity)
            }
        )
    }

    fun getFinishTodoByDate(date: String,finishTodoLiveData: MutableLiveData<List<FinishTodoEntity>>) {
        launch(
            block = {
                DatabaseManager.get().queryFinishTodoEntityByDate(date)
            },
            success = {
                finishTodoLiveData.postValue(it)
            }
        )
    }

}