package com.zjl.mytomato.ui.statistics

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.zjl.mytomato.BaseViewModel
import com.zjl.mytomato.ui.todolist.TodoListRepo

class StatisticVm : BaseViewModel() {
    private val repo by lazy { TodoListRepo(viewModelScope) }
    val finishTodoNum = MutableLiveData<Int>()
    val totalTime = MutableLiveData<Int>()
    val averageTime = MutableLiveData<Int>()
    val dayNum = MutableLiveData<Int>()
    val dayTime = MutableLiveData<Int>()
    val pieChartDate = MutableLiveData<Map<String, Int>>()

    fun getFinishTodoNum() {
        repo.getFinishTodoNum(finishTodoNum)
    }

    fun getTotalTime() {
        repo.getTotalTime(totalTime)
    }

    fun getTotalAverageTime() {
        repo.getTotalAverageTime(averageTime)
    }

    fun getNumByDate(date: String) {
        repo.getNumByDate(date, dayNum)
    }

    fun getTimeByDate(date: String) {
        repo.getTimeByDate(date, dayTime)
    }

    fun getPieChartData(date: String) {
        repo.getPieChartData(date, pieChartDate)
    }

    fun getAppUsedTime(date: String) {

    }
}