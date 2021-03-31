package com.zjl.mytomato

import android.app.Application
import android.content.Context
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
    }

    companion object {
        var isLocking: Boolean = false
        lateinit var appContext: Context
    }
}