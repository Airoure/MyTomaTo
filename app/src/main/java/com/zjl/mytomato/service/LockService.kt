package com.zjl.mytomato.service

import android.app.Service
import android.content.Intent
import android.os.CountDownTimer
import android.os.IBinder
import android.util.Log
import com.zjl.mytomato.entity.TodoEntity
import com.zjl.mytomato.ui.todo.WorkWindow

class LockService : Service() {
    private var mWorkWindow: WorkWindow? = null
    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        addNotification()
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val todo = intent!!.getParcelableExtra<TodoEntity>("todoEntity")
        if(mWorkWindow != null){
            mWorkWindow!!.remove()
            mWorkWindow = null
        }
        mWorkWindow = WorkWindow(this,todo!!)
        mWorkWindow!!.show()
        return START_STICKY
    }

    override fun onDestroy() {
        if(mWorkWindow!=null){
            mWorkWindow!!.remove()
            mWorkWindow = null
        }
        super.onDestroy()
    }

    private fun addNotification() {

    }
}