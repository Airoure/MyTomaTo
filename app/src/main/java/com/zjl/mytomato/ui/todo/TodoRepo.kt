package com.zjl.mytomato.ui.todo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.zjl.mytomato.BaseRepo
import com.zjl.mytomato.common.Constant
import com.zjl.mytomato.database.DatabaseManager
import com.zjl.mytomato.entity.TodoEntity
import kotlinx.coroutines.CoroutineScope

class TodoRepo(coroutineScope: CoroutineScope) : BaseRepo(coroutineScope) {

    fun addTodo(
            todoEntity: TodoEntity,
            messageLiveData: MutableLiveData<Int>,
            todoLiveData: MutableLiveData<TodoEntity>
    ) {
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
                    it.printStackTrace()
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
                    it.printStackTrace()
                }
        )
    }

    fun getAllTodoInset(todoLiveData: MutableLiveData<MutableSet<TodoEntity>>) {
        launch(
                block = {
                    DatabaseManager.get()
                            .queryTodoEntityAllInSet()
                },
                success = {
                    todoLiveData.postValue(it)
                },
                fail = {
                    it.printStackTrace()
                }
        )
    }

    fun deleteTodo(todoEntity: TodoEntity, removeLiveData: MutableLiveData<TodoEntity>) {
        launch(
                block = {
                    DatabaseManager.get()
                            .deleteTodoEntity(todoEntity)
                },
                success = {
                    removeLiveData.postValue(todoEntity)
                },
                fail = {

                }

        )
    }

    fun saveTodo(
            todoEntity: TodoEntity,
            todoLiveData: MutableLiveData<MutableList<TodoEntity>>,
            messageLiveData: MutableLiveData<Int>
    ) {
        launch(
                block = {
                    DatabaseManager.get()
                            .updateTodoEntity(todoEntity)
                },
                success = {
                    getAllTodo(todoLiveData)
                },
                fail = {
                    Log.e("123", "fail")
                    messageLiveData.postValue(Constant.UPDATE_TODO_FAIL)
                }
        )
    }

}