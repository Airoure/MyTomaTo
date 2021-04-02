package com.zjl.mytomato.ui.mine

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.zjl.mytomato.BaseRepo
import com.zjl.mytomato.database.DatabaseManager
import com.zjl.mytomato.entity.TodoEntity
import kotlinx.coroutines.CoroutineScope

class MineRepo(coroutineScope: CoroutineScope) : BaseRepo(coroutineScope) {

    fun getAllTodoName(todoEntities : MutableLiveData<List<TodoEntity>>) {
        launch(
                block = {
                    DatabaseManager.get().queryTodoEntityAll()
                },
                success = {
                    todoEntities.postValue(it)
                }
        )
    }
}