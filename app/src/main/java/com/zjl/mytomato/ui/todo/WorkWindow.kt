package com.zjl.mytomato.ui.todo

import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.view.WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
import android.view.WindowManager.LayoutParams.TYPE_TOAST
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.blankj.utilcode.util.AppUtils
import com.bumptech.glide.Glide
import com.zjl.mytomato.R
import com.zjl.mytomato.database.DatabaseManager
import com.zjl.mytomato.databinding.WindowWorkBinding
import com.zjl.mytomato.entity.TodoEntity
import com.zjl.mytomato.ui.main.MainActivity
import com.zjl.mytomato.view.TipView

@Suppress("DEPRECATION")
class WorkWindow {
    private var windowManager: WindowManager
    private var mContext: Context
    private var workView: View
    private var mLayoutParam: WindowManager.LayoutParams = WindowManager.LayoutParams()
    private var isAdded = false
    private var todoEntity: TodoEntity

    constructor(context: Context, todoEntity: TodoEntity) {
        mContext = context
        this.todoEntity = todoEntity
        windowManager =
            mContext.applicationContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val tipView = TipView(
            mContext,
            content = "确定要退出吗",
        )
        tipView.setOnConfirmClickListener {
            mContext.startActivity(
                Intent(mContext,MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            )
            windowManager.removeView(tipView)
            remove()
        }
        val ui = WindowWorkBinding.inflate(LayoutInflater.from(context)).apply {
            ivStop.setOnClickListener {
                windowManager.addView(tipView, mLayoutParam)

            }
            imgBack.apply {
                Glide.with(context)
                    .load("https://source.unsplash.com/1600x900/?nature/${todoEntity.imageUrl}")
                    .placeholder(resources.getDrawable(R.color.black))
                    .into(this)
            }
            tvTodoName.text = todoEntity.name
        }
        workView = ui.root
    }

    @Synchronized
    fun show() {
        if (isAdded) {
            workView.visibility = View.VISIBLE
            return
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mLayoutParam.type = TYPE_APPLICATION_OVERLAY
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                mLayoutParam.layoutInDisplayCutoutMode =
                    WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            mLayoutParam.type = TYPE_TOAST
        } else {
            mLayoutParam.type = WindowManager.LayoutParams.TYPE_PHONE
        }
        mLayoutParam.packageName = AppUtils.getAppPackageName()
        mLayoutParam.flags =
            mLayoutParam.flags or WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED
        mLayoutParam.flags =
            mLayoutParam.flags or (WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL)
        mLayoutParam.flags =
            mLayoutParam.flags or (WindowManager.LayoutParams.FLAG_FULLSCREEN or WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR or WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN)
        mLayoutParam.width = WindowManager.LayoutParams.MATCH_PARENT
        mLayoutParam.height = WindowManager.LayoutParams.MATCH_PARENT
        mLayoutParam.format = PixelFormat.TRANSPARENT
        try {
            mLayoutParam.x = 0
            mLayoutParam.y = 0
            windowManager.addView(workView, mLayoutParam)
            isAdded = true
            val time = (todoEntity.hour * 60 * 60 + todoEntity.minute * 60 + todoEntity.second) * 1000L
            Log.e("123","456")
            val timer = object : CountDownTimer(time, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    val hour = millisUntilFinished / (1000 * 60 * 60)
                    val minute = (millisUntilFinished - hour * 1000 * 60 * 60) / (1000 * 60)
                    val second = (millisUntilFinished - minute * 1000 * 60 - hour * 1000 * 60 * 60) / 1000
                    todoEntity.second = second.toInt()
                    todoEntity.minute = minute.toInt()
                    todoEntity.hour = hour.toInt()
                    
                    Log.e("second","$hour 时$minute 分$second 秒")
                }
                override fun onFinish() {

                }
            }
            timer.start()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(mContext, "似乎没有悬浮窗权限哦！", Toast.LENGTH_SHORT).show()
        }
    }

    fun remove() {
        if (workView.isAttachedToWindow) {
            windowManager.removeView(workView)
            isAdded = false
        }
    }

}