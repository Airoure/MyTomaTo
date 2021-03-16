package com.zjl.mytomato.util

import android.app.usage.UsageStatsManager
import android.content.ComponentName
import android.content.Context

class RunningTaskUtil(context: Context) {
    private lateinit var mUsageStatsManager: UsageStatsManager

    private var topComponentName: ComponentName? = null

    init {
        mUsageStatsManager = context.applicationContext.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager

    }
}