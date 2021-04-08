package com.zjl.mytomato.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.zjl.mytomato.entity.TimedTaskEntity

class KeepAliveService : Service() {
    private var timedTaskList = mutableListOf<TimedTaskEntity>()
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        while (true) {
            for (item in timedTaskList) {

            }
        }
        return START_STICKY
    }


}