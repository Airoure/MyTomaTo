package com.zjl.mytomato.view

import android.content.Context
import android.graphics.PixelFormat
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.view.WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
import android.view.WindowManager.LayoutParams.TYPE_TOAST
import android.widget.Toast
import com.blankj.utilcode.util.AppUtils
import com.bumptech.glide.Glide
import com.zjl.mytomato.R
import com.zjl.mytomato.databinding.WindowWorkBinding

@Suppress("DEPRECATION")
class WorkWindow {
    private var windowManager: WindowManager
    private var mContext: Context
    private var workView: View
    private var mLayoutParam: WindowManager.LayoutParams = WindowManager.LayoutParams()
    private var isAdded = false

    constructor(context: Context, url: String) {
        mContext = context
        windowManager =
            mContext.applicationContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val ui = WindowWorkBinding.inflate(LayoutInflater.from(context))
        ui.btnStop.setOnClickListener {
            remove()
        }
        ui.imgBack.apply {
            Glide.with(context)
                .load("https://source.unsplash.com/1600x900/?nature/${url}")
                .placeholder(resources.getDrawable(R.color.black))
                .into(this)
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
        } catch (e: Exception) {
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