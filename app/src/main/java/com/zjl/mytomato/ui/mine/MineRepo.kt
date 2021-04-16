package com.zjl.mytomato.ui.mine

import androidx.lifecycle.MutableLiveData
import com.zjl.mytomato.BaseRepo
import com.zjl.mytomato.common.Constant
import com.zjl.mytomato.database.DatabaseManager
import com.zjl.mytomato.entity.TimedTaskEntity
import com.zjl.mytomato.entity.TodoEntity
import kotlinx.coroutines.CoroutineScope

class MineRepo(coroutineScope: CoroutineScope) : BaseRepo(coroutineScope) {

    fun getAllTodoName(todoEntities: MutableLiveData<List<TodoEntity>>) {
        launch(
            block = {
                DatabaseManager.get().queryTodoEntityAll()
            },
            success = {
                todoEntities.postValue(it)
            }
        )
    }

    fun addTimedTask(
        timedTaskEntity: TimedTaskEntity,
        messageLiveData: MutableLiveData<Int>,
        addedTimedTaskEntity: MutableLiveData<TimedTaskEntity>
    ) {
        launch(
            block = {
                DatabaseManager.get().insertTimedTaskEntity(timedTaskEntity)
            },
            success = {
                messageLiveData.postValue(Constant.ADD_TIMED_TASK_SUCCESS)
                addedTimedTaskEntity.postValue(timedTaskEntity)
            },
            fail = {
                messageLiveData.postValue(Constant.ADD_TIMED_TASK_FAIL)
            }
        )
    }

    fun getAllTimedTask(initTimedTaskEntities: MutableLiveData<List<TimedTaskEntity>>) {
        launch(
            block = {
                DatabaseManager.get().queryTimedTaskEntityAll()
            },
            success = {
                initTimedTaskEntities.postValue(it)
            }
        )
    }

    fun changeTimedTaskEnable(timedTaskEntity: TimedTaskEntity) {
        launch(
            block = {
                DatabaseManager.get().changeTimedTaskEnable(timedTaskEntity)
            }
        )
    }

    fun deleteTimeTaskEntity(timedTaskEntity: TimedTaskEntity) {
        launch(
            block = {
                DatabaseManager.get().deleteTimeTaskEntity(timedTaskEntity)
            },

            )
    }

}