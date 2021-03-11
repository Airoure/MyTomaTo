package com.zjl.mytomato.ui.todo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.zjl.mytomato.BaseRepo
import com.zjl.mytomato.common.Constant
import com.zjl.mytomato.database.DatabaseManager
import com.zjl.mytomato.entity.TodoEntity
import kotlinx.coroutines.CoroutineScope

class TodoRepo(coroutineScope: CoroutineScope): BaseRepo(coroutineScope){

    fun addTodo(todoEntity: TodoEntity,messageLiveData: MutableLiveData<Int>,todoLiveData: MutableLiveData<TodoEntity>){
        launch(
            block = {
                DatabaseManager.get()
                    .insertTodoEntity(todoEntity)
            },
            success = {
                messageLiveData.postValue(Constant.ADD_TODO_SUCCESS)
                todoLiveData.postValue(todoEntity)
            },
            fail = {
                messageLiveData.postValue(Constant.ADD_TODO_FAIL)
            }
        )
    }

    fun getAllTodo(todoLiveData: MutableLiveData<MutableList<TodoEntity>>) {
        launch(
            block = {
                DatabaseManager.get()
                    .queryTodoEntityAll()
            },
            success = {
                todoLiveData.postValue(it)
            },
            fail = {

            }
        )
    }

}