package com.zjl.mytomato.util

import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.pm.ApplicationInfo
import android.util.Log
import androidx.core.content.ContextCompat
import com.zjl.mytomato.App
import java.util.*

object AppUsedUtil {
    private val mUsageStatsManager = ContextCompat.getSystemService(App.appContext, UsageStatsManager::class.java)

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

    fun getAppUsedTime(context: Context): Map<String, Long> {
        val appMap = TreeMap<String, Long>()
        val mPackageManager = context.packageManager
        val queryUsageStates = mUsageStatsManager?.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, CalendarUtil.getTodayStartTime(), System.currentTimeMillis())
        if (!queryUsageStates.isNullOrEmpty()) {
            for (item in queryUsageStates) {
                val app = mPackageManager!!.getApplicationInfo(item.packageName, 0)
                if (app.flags and ApplicationInfo.FLAG_SYSTEM != 0) {
                    continue
                }
                Log.e("123", "${app.loadLabel(mPackageManager)}...${item.totalTimeInForeground / 1000 / 60}")
                appMap[app.loadLabel(mPackageManager).toString()] = item.totalTimeInForeground / 1000 / 60
            }
        }

        return appMap
    }
}