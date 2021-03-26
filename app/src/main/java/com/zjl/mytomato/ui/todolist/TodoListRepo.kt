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

    fun getFinishTodoByDate(
            date: String,
            finishTodoLiveData: MutableLiveData<List<FinishTodoEntity>>
    ) {
        launch(
                block = {
                    DatabaseManager.get().queryFinishTodoEntityByDate(date)
                },
                success = {
                    finishTodoLiveData.postValue(it)
                }
        )
    }

    fun getFinishTodoNum(finishTodoNum: MutableLiveData<Int>) {
        launch(
                block = {
                    DatabaseManager.get().getFinishTodoNum()
                },
                success = {
                    finishTodoNum.postValue(it)
                }
        )
    }

    fun getTotalTime(totalTime: MutableLiveData<Int>) {
        launch(
                block = {
                    DatabaseManager.get().getTotalTime()
                },
                success = {
                    totalTime.postValue(it)
                }
        )
    }

    fun getTotalAverageTime(averageTime: MutableLiveData<Int>) {
        launch(
                block = {
                    DatabaseManager.get().getAverageTime()
                },
                success = {
                    averageTime.postValue(it)
                }
        )

    }

    fun getNumByDate(date: String, dayNum: MutableLiveData<Int>) {
        launch(
                block = {
                    DatabaseManager.get().getNumByDate(date)
                },
                success = {
                    dayNum.postValue(it)
                }
        )
    }

    fun getTimeByDate(date: String, dayTime: MutableLiveData<Int>) {
        launch(
                block = {
                    DatabaseManager.get().getTimeByDate(date)
                },
                success = {
                    dayTime.postValue(it)
                }
        )

    }

    fun getPieChartData(date: String, pieChartData: MutableLiveData<Map<String, Int>>) {
        launch(
                block = {
                    DatabaseManager.get().getFinishTodoByDate(date)
                },
                success = {
                    pieChartData.postValue(
                            it.map { i ->
                                i.name to i.time
                            }.toMap())
                }
        )
    }

}