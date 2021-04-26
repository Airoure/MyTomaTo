package com.zjl.mytomato

import android.app.Application
import android.content.Context
import cn.bmob.v3.Bmob
import cn.bmob.v3.BmobConfig
import com.jaredrummler.cyanea.Cyanea
import com.tencent.bugly.Bugly
import com.zjl.mytomato.database.DatabaseManager


class App : Application() {
    override fun onCreate() {
        super.onCreate()
        DatabaseManager.init(this)
        appContext = this
        Cyanea.init(this, resources)
        Bugly.init(applicationContext, "ddeb60568a", true)
        val bmobConfig = BmobConfig.Builder(this)
                .setApplicationId("4ee1de618575da974ffbbb2e729e7a1a")
                .setConnectTimeout(30)
                .build()
        Bmob.resetDomain("https://open3.bmob.cn/8/")
        Bmob.initialize(bmobConfig)

    }

    companion object {
        var isLocking: Boolean = false
        lateinit var appContext: Context
    }
}