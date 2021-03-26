package com.zjl.mytomato.util

import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import androidx.core.content.ContextCompat
import com.zjl.mytomato.App

object AppUsedUtil {
    private val mUsageStatsManager =
            ContextCompat.getSystemService(App.appContext, UsageStatsManager::class.java)

    fun getTopAppName(): String? {
        val now = System.currentTimeMillis()
        val queryUsageStats =
                mUsageStatsManager?.queryUsageStats(UsageStatsManager.INTERVAL_BEST, 0, now)
        var recentTask: UsageStats? = null
        if (!queryUsageStats.isNullOrEmpty()) {
            for (item in queryUsageStats) {
                if (recentTask == null || recentTask.lastTimeUsed < item.lastTimeUsed) {
                    recentTask = item
                }
            }
        }
        return recentTask?.packageName
    }

    fun getDayAppUsed() {

    }


}