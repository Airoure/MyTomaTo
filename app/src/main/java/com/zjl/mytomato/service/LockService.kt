package com.zjl.mytomato.service

import android.app.Service
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
    private lateinit var workView: View
    private lateinit var realUi: WindowWorkBinding
    private lateinit var mWindowManager: WindowManager
    private var mLayoutParam: WindowManager.LayoutParams = WindowManager.LayoutParams()
    override fun onBind(intent: Intent): IBinder? {
        return null

    }

    override fun onCreate() {
        super.onCreate()
        mWindowManager = getSystemService(WindowManager::class.java)
        initView()
    }

    private fun initView() {
        val tipView = TipView(
            this,
            content = "确定要退出吗",
        ) {
            mWindowManager.removeView(it)
        }
        tipView.setOnConfirmClickListener {
            startActivity(
                Intent(this, MainActivity::class.java)
            )
            mWindowManager.removeView(tipView)
            removeView()
        }
        realUi = WindowWorkBinding.inflate(LayoutInflater.from(this)).apply {
            ivStop.setOnClickListener {
                mWindowManager.addView(tipView, mLayoutParam)

            }
            imgBack.apply {
                Glide.with(applicationContext)
                    .load("https://source.unsplash.com/1600x900/?nature/${LockActivity.todoEntity?.imageUrl}")
                    .placeholder(resources.getDrawable(R.color.black))
                    .into(this)
            }
            tvTodoName.text = LockActivity.todoEntity!!.name
        }
        workView = realUi.root

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mLayoutParam.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                mLayoutParam.layoutInDisplayCutoutMode =
                    WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
            }
        } else {
            mLayoutParam.type = WindowManager.LayoutParams.TYPE_TOAST
        }
        mLayoutParam.packageName = AppUtils.getAppPackageName()
        mLayoutParam.flags = mLayoutParam.flags or
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED or
                (WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                        WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL) or
                (WindowManager.LayoutParams.FLAG_FULLSCREEN or
                        WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR or
                        WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION or
                        WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN)
        mLayoutParam.width = WindowManager.LayoutParams.MATCH_PARENT
        mLayoutParam.height = WindowManager.LayoutParams.MATCH_PARENT
        mLayoutParam.format = PixelFormat.TRANSPARENT

    }

    fun removeView() {

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val homePackage = packageManager.resolveActivity(
            Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME),
            0
        )!!.activityInfo.packageName
        val timerTask = object : TimerTask() {
            override fun run() {
                while (App.isLocking) {
                    Log.e("123456", "${AppUsedUtil.getTopAppName()}   $homePackage ${AppUsedUtil.getTopAppName()?.contains("launcher")!!}")
                    if (AppUsedUtil.getTopAppName()
                            ?.contains("home")!! || AppUsedUtil.getTopAppName()
                            ?.contains("launcher")!!
                    ) {
                        continue
                    }
                    startActivity(
                        Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME)
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    )
                    Thread.sleep(500)
                }
            }
        }
        Timer().schedule(timerTask, 0)
        return START_STICKY
    }
}