package com.zjl.mytomato

import android.app.Application
import com.zjl.mytomato.database.DatabaseManager

class App :Application() {
    override fun onCreate() {
        super.onCreate()
        DatabaseManager.init(this)
    }
}