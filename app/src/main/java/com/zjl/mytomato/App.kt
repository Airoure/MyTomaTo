package com.zjl.mytomato

import android.app.Application
import android.content.Context
import com.zjl.mytomato.database.DatabaseManager

class App :Application() {
    override fun onCreate() {
        super.onCreate()
        DatabaseManager.init(this)
        appContext = this
    }

    companion object{
        var isLocking: Boolean = false
        lateinit var appContext:Context
    }
}