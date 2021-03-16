package com.zjl.mytomato.ui.lock

import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Intent
import android.os.CountDownTimer
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.zjl.mytomato.App
import com.zjl.mytomato.BaseViewModel
import com.zjl.mytomato.database.DatabaseManager
import com.zjl.mytomato.entity.TodoEntity
import com.zjl.mytomato.worker.ActivityGuard
import kotlinx.coroutines.*

class LockVm : BaseViewModel() {
    private var timer: CountDownTimer? = null
    @Volatile private var timerFlag = true
    @Volatile private var finishFlag = false

    val timeLiveData = MutableLiveData<String>()
    val finishLiveData = MutableLiveData<Boolean>()
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
                todoEntity.minute = minute.toInt()
                todoEntity.second = second.toInt()
                todoEntity.hour = hour.toInt()
            }

            override fun onFinish() {
                timerFlag = false
                finishFlag = true
            }
        }
        timer!!.start()

        viewModelScope.launch {

            withContext(Dispatchers.IO){
                while (timerFlag){
                    DatabaseManager.get().updateTodoEntity(todoEntity)
                    delay(1000)
                }
                if(finishFlag){
                    DatabaseManager.get().deleteTodoEntity(todoEntity)
                }
                finishLiveData.postValue(true)
            }
        }
    }

    fun stopCountDown() {
        timer!!.cancel()
        App.isLocking = false
        timerFlag = false
    }
}