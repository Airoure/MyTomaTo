package com.zjl.mytomato.ui.lock

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.zjl.mytomato.App
import com.zjl.mytomato.BaseViewModel
import com.zjl.mytomato.entity.FinishTodoEntity
import com.zjl.mytomato.entity.TodoEntity
import com.zjl.mytomato.ui.todolist.TodoListRepo
import com.zjl.mytomato.util.CoroutineScopeTimer
import com.zjl.mytomato.util.SpUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class LockVm : BaseViewModel() {

    private val repo by lazy { TodoListRepo(viewModelScope) }
    private val onceWorkTime by lazy { SpUtil.getWorkTime() }
    private val onceRestTime by lazy { SpUtil.getRestTime() }
    private var cancel = false
    val timeLiveData = MutableLiveData<String>()
    val finishLiveData = MutableLiveData<Boolean>()
    fun startCountDonw(todoEntity: TodoEntity) {
        viewModelScope.launch {
            App.isLocking = true
            var totalWorkTime = (todoEntity.hour * 60 * 60 + LockActivity.todoEntity!!.minute * 60 + LockActivity.todoEntity!!.second) * 1000L
            var nowWorkTime = totalWorkTime
            var nowRestTime = onceRestTime
            val workTimer = object : CoroutineScopeTimer() {
                override fun run() {
                    val hour = nowWorkTime / (1000 * 60 * 60)
                    val minute = (nowWorkTime - hour * 1000 * 60 * 60) / (1000 * 60)
                    val second = (nowWorkTime - minute * 1000 * 60 - hour * 1000 * 60 * 60) / 1000
                    timeLiveData.postValue("$hour 时$minute 分$second 秒")
                    nowWorkTime -= 1000
                    if (nowWorkTime == 0L) {
                        totalWorkTime = 0L
                    }
                }

            }
            val restTimer = object : CoroutineScopeTimer() {
                override fun run() {
                    val hour = nowRestTime / (1000 * 60 * 60)
                    val minute = (nowRestTime - hour * 1000 * 60 * 60) / (1000 * 60)
                    val second = (nowRestTime - minute * 1000 * 60 - hour * 1000 * 60 * 60) / 1000
                    timeLiveData.postValue("休息$minute 分$second 秒")
                    nowRestTime -= 1000
                    if (nowRestTime == 0L) {
                        totalWorkTime = nowWorkTime
                    }
                }

            }
            withContext(Dispatchers.IO) {
                while (!cancel) {
                    if (totalWorkTime == 0L) {
                        //计时结束
                        val dateFormat = SimpleDateFormat("yyyy年MM月dd日")
                        val timeFormat = SimpleDateFormat("HH:mm")
                        val date = dateFormat.format(Date())
                        val time = timeFormat.format(Date())
                        repo.addFinishTodo(
                                FinishTodoEntity(
                                        todoEntity.name,
                                        todoEntity.imageUrl,
                                        date,
                                        time,
                                        todoEntity.hour,
                                        todoEntity.minute
                                )
                        )
                        finishLiveData.postValue(true)

                    } else {
                        if ((totalWorkTime - nowWorkTime) % onceWorkTime < 1000 && totalWorkTime != nowWorkTime) {
                            //休息时间
                            restTimer.run()
                        } else {
                            nowRestTime = onceRestTime
                            workTimer.run()
                        }
                    }
                    delay(1000)
                }
            }
        }
    }

    fun stopCountDown() {
        App.isLocking = false
        cancel = true
    }
}