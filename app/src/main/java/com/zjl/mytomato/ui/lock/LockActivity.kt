package com.zjl.mytomato.ui.lock

import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.blankj.utilcode.util.AppUtils
import com.bumptech.glide.Glide
import com.zjl.mytomato.App
import com.zjl.mytomato.R
import com.zjl.mytomato.databinding.WindowWorkBinding
import com.zjl.mytomato.entity.TodoEntity
import com.zjl.mytomato.ui.main.MainActivity
import com.zjl.mytomato.view.TipView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking


class LockActivity : AppCompatActivity() {
    private lateinit var workView: View
    private lateinit var realUi: WindowWorkBinding
    private var mLayoutParam: WindowManager.LayoutParams = WindowManager.LayoutParams()
    private lateinit var lockVm: LockVm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lock)
        lockVm = ViewModelProvider(this).get(LockVm::class.java)
        val tipView = TipView(
            this,
            content = "确定要退出吗",
        ){
            windowManager.removeView(it)
        }
        tipView.setOnConfirmClickListener {
            startActivity(
                Intent(this, MainActivity::class.java)
            )
            windowManager.removeView(tipView)
            removeView()
        }
        realUi = WindowWorkBinding.inflate(layoutInflater).apply {
            ivStop.setOnClickListener {
                windowManager.addView(tipView, mLayoutParam)

            }
            imgBack.apply {
                Glide.with(applicationContext)
                    .load("https://source.unsplash.com/1600x900/?nature/${todoEntity?.imageUrl}")
                    .placeholder(resources.getDrawable(R.color.black))
                    .into(this)
            }
            tvTodoName.text = todoEntity!!.name
        }
        workView = realUi.root
        lockVm.timeLiveData.observe(this, {
            realUi.tvTime.text = it
        })

        lockVm.finishLiveData.observe(this, {
            if (it) {
                removeView()
            }
        })
        show()
    }

    @Synchronized
    fun show() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mLayoutParam.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                mLayoutParam.layoutInDisplayCutoutMode =
                    WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
            }
        } else{
            mLayoutParam.type = WindowManager.LayoutParams.TYPE_TOAST
        }
        mLayoutParam.packageName = AppUtils.getAppPackageName()
        mLayoutParam.flags = mLayoutParam.flags or
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED or
                (WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                        WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL)or
                (WindowManager.LayoutParams.FLAG_FULLSCREEN or
                        WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR or
                        WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION or
                        WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN)
        mLayoutParam.width = WindowManager.LayoutParams.MATCH_PARENT
        mLayoutParam.height = WindowManager.LayoutParams.MATCH_PARENT
        mLayoutParam.format = PixelFormat.TRANSPARENT
        try {
            mLayoutParam.x = 0
            mLayoutParam.y = 0
            if(!workView.isAttachedToWindow){
                windowManager.addView(workView, mLayoutParam)
                lockVm.startCountDonw(todoEntity!!)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "似乎没有悬浮窗权限哦！", Toast.LENGTH_SHORT).show()
        }

    }

    private fun removeView() {
        if (workView.isAttachedToWindow) {
            windowManager.removeView(workView)
            lockVm.stopCountDown()
            MainActivity.open(this)
            finish()
        }
    }

    companion object {
        var todoEntity: TodoEntity? = null
        fun open(context: Context, todoEntity: TodoEntity) {
            context.startActivity(
                Intent(context, LockActivity::class.java).putExtra(
                    "todoEntity",
                    todoEntity
                )
            )
            this.todoEntity = todoEntity
        }
    }
}