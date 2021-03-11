package com.zjl.mytomato.ui.launch

import android.provider.Settings
import android.widget.Toast
import com.zjl.mytomato.BaseActivity
import com.zjl.mytomato.databinding.ActivityLaunchBinding
import com.zjl.mytomato.ui.main.MainActivity
import com.zjl.mytomato.util.SettingPageUtil
import com.zjl.mytomato.view.CommonDialog
import java.util.*

class LaunchActivity : BaseActivity<ActivityLaunchBinding>() {
    override fun initUI() = ActivityLaunchBinding.inflate(layoutInflater)

    override fun init() {
        if (Settings.canDrawOverlays(this)) {
            Timer().schedule(
                object : TimerTask() {
                    override fun run() {
                        MainActivity.open(this@LaunchActivity)
                        finish()
                    }

                },2000
            )
        } else {
            Toast.makeText(this, "没有悬浮窗权限", Toast.LENGTH_SHORT).show()
            CommonDialog(this, "没有悬浮窗权限", "请前往打开悬浮窗权限!") {
                SettingPageUtil.tryJumpToPermissionPage(this)
            }.show()
        }

    }
}