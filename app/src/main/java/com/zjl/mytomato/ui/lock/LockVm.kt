package com.zjl.mytomato.ui.lock

import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.zjl.mytomato.App
import com.zjl.mytomato.BaseViewModel
import com.zjl.mytomato.entity.FinishTodoEntity
import com.zjl.mytomato.entity.TodoEntity
import com.zjl.mytomato.ui.todolist.TodoListRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class LockVm : BaseViewModel() {

    private val repo by lazy { TodoListRepo(viewModelScope) }
    private var timer: CountDownTimer? = null

    val timeLiveData = MutableLiveData<String>()
    val finishLiveData = MutableLiveData<Boolean>()
    val messageLiveData = MutableLiveData<Int>()
    fun startCountDonw(todoEntity: TodoEntity) {
        App.isLocking = true
        val time =
            (LockActivity.todoEntity!!.hour * 60 * 60 + LockActivity.todoEntity!!.minute * 60 + LockActivity.todoEntity!!.second) * 1000L
        timer = object : CountDownTimer(time, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val hour = millisUntilFinished / (1000 * 60 * 60)
                val minute = (millisUntilFinished - hour * 1000 * 60 * 60) / (1000 * 60)
                val second =
                    (millisUntilFinished - minute * 1000 * 60 - hour * 1000 * 60 * 60) / 1000
                timeLiveData.postValue("$hour 时$minute 分$second 秒")
            }

            override fun onFinish() {
                viewModelScope.launch {
                    withContext(Dispatchers.Default) {
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
                    }

                }

            }
        }
        timer!!.start()
    }

    fun stopCountDown() {
        timer!!.cancel()
        App.isLocking = false
    }
}