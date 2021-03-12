package com.zjl.mytomato.ui.Lock

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.zjl.mytomato.BaseViewModel
import com.zjl.mytomato.database.DatabaseManager
import com.zjl.mytomato.entity.TodoEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LockVm : BaseViewModel() {
    private var timer: CountDownTimer? = null
    @Volatile private var timerFlag = true
    @Volatile private var finishFlag = false
    val timeLiveData = MutableLiveData<String>()
    val finishLiveData = MutableLiveData<Boolean>()
    fun startCountDonw(todoEntity: TodoEntity) {
        viewModelScope.launch {
            val time =
                (LockActivity.todoEntity!!.hour * 60 * 60 + LockActivity.todoEntity!!.minute * 60 + LockActivity.todoEntity!!.second) * 1000L
            Log.e("123", "456")
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
            withContext(Dispatchers.IO){
                while (timerFlag){
                    DatabaseManager.get().updateTodoEntity(todoEntity)
                    delay(1000)
                    Log.e("保存","baoc")
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
        timerFlag = false
    }
}