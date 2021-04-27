package com.zjl.mytomato.ui.launch

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.AppOpsManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Process
import android.provider.Settings
import android.view.animation.BounceInterpolator
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
            val appOps = getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
            val mode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                appOps.unsafeCheckOpNoThrow(
                    AppOpsManager.OPSTR_GET_USAGE_STATS,
                    Process.myUid(),
                    packageName
                )
            } else {
                appOps.checkOpNoThrow(
                    AppOpsManager.OPSTR_GET_USAGE_STATS,
                    Process.myUid(),
                    packageName
                )
            }
            if (mode == AppOpsManager.MODE_ALLOWED) {
                val ivAnimator = ObjectAnimator.ofFloat(ui.ivTomato, "rotation", 0f, 360f)
                val tvAnimotor = ObjectAnimator.ofFloat(ui.tvTomato, "translationX", -500f, 0f)
                AnimatorSet().apply {
                    playTogether(tvAnimotor, ivAnimator)
                    interpolator = BounceInterpolator()
                    duration = 1500
                    addListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            super.onAnimationEnd(animation)
                            MainActivity.open(this@LaunchActivity)
                            finish()
                        }
                    })
                    start()
                }
            } else {
                CommonDialog(
                    this,
                    content = "需要同意该app使用app使用时间读取权限",
                    touchOutCamcel = false,
                    listener = object : CommonDialog.DialogClickListener {
                        override fun onConfirm() {
                            val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
                            startActivityForResult(intent, 0)
                        }

                        override fun onCancel() {
                            finish()
                        }

                    }).show()
            }
        } else {
            Toast.makeText(this, "没有悬浮窗权限", Toast.LENGTH_SHORT).show()
            CommonDialog(
                this,
                "没有悬浮窗权限",
                "请前往打开悬浮窗权限!",
                listener = object : CommonDialog.DialogClickListener {
                    override fun onConfirm() {
                        SettingPageUtil.tryJumpToPermissionPage(this@LaunchActivity)
                    }

                    override fun onCancel() {
                        finish()
                    }

                }).show()
        }


    }
}