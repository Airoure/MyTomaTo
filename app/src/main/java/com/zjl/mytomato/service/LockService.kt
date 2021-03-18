package com.zjl.mytomato.service

import android.app.ActivityManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.graphics.drawable.toBitmap
import com.blankj.utilcode.util.AppUtils
import com.bumptech.glide.Glide
import com.zjl.mytomato.App
import com.zjl.mytomato.R
import com.zjl.mytomato.databinding.WindowWorkBinding
import com.zjl.mytomato.ui.lock.LockActivity
import com.zjl.mytomato.ui.main.MainActivity
import com.zjl.mytomato.util.AppUsedUtil
import com.zjl.mytomato.view.TipView
import java.util.*


class LockService : Service() {

    private lateinit var notificationManager: NotificationManager
    private val notificationFlag = 1
    override fun onBind(intent: Intent): IBinder? {
        return null

    }

    override fun onCreate() {
        super.onCreate()
        addNotification()
    }

    private fun addNotification() {
        createNotificationChannel()

        val mBuilder = NotificationCompat.Builder(applicationContext,"MyTomato")
            .setSmallIcon(R.drawable.tomato)
            .setLargeIcon(resources.getDrawable(R.drawable.tomato).toBitmap())
            .setContentTitle("Tomato")
            .setContentText("正在运行")
            .setWhen(System.currentTimeMillis())
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notification = mBuilder.setAutoCancel(false).build()
        startForeground(notificationFlag,notification)
    }

    private fun createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            val chanel = NotificationChannel("MyTomato","name",NotificationManager.IMPORTANCE_DEFAULT)
            chanel.description = "des"
            chanel.setShowBadge(false)
            notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(chanel)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val homePackage = packageManager.resolveActivity(
            Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME),
            0
        )!!.activityInfo.packageName
        Timer().schedule(
                object :TimerTask(){
                    override fun run() {
                        while (App.isLocking) {
                            Thread.sleep(100)
//                            Log.e("777","${Thread.currentThread().name}")
//                            Log.e("123456", "${AppUsedUtil.getTopAppName()}   $homePackage ${AppUsedUtil.getTopAppName()?.equals("com.zjl.mytomato")!!}")
                            if (AppUsedUtil.getTopAppName()?.equals("com.zjl.mytomato")!!) {
                                continue
                            }
                            startActivity(Intent(this@LockService,LockActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
                        }
                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                            stopForeground(STOP_FOREGROUND_REMOVE)
                        }
                    }

                },0
        )
        return START_STICKY
    }
}