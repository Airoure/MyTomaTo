package com.zjl.mytomato.util

import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.pm.ApplicationInfo
import android.util.Log
import androidx.core.content.ContextCompat
import com.zjl.mytomato.App
import java.text.SimpleDateFormat
import java.util.*

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

    fun getPhoneWeekUsedTime(context: Context): Map<String, Int> {
        val phoneUsedTimeMap = mutableMapOf<String, Int>()
        val mPackageManager = context.packageManager
        val thisWeekDayStart = CalendarUtil.getAllThisWeekDayStart()
        val thisWeekDayEnd = CalendarUtil.getAllThisWeekDayEnd()
        for (i in 0..6) {
            val queryUsageStates = mUsageStatsManager?.queryUsageStats(
                UsageStatsManager.INTERVAL_DAILY,
                thisWeekDayStart[i],
                thisWeekDayEnd[i]
            )
            var totalTime = 0L
            if (!queryUsageStates.isNullOrEmpty()) {
                for (item in queryUsageStates) {
                    val app = mPackageManager!!.getApplicationInfo(item.packageName, 0)
                    if (app.flags and ApplicationInfo.FLAG_SYSTEM != 0) {
                        continue
                    }
                    totalTime += item.totalTimeInForeground
                }
            }
            val dateString = SimpleDateFormat("MM-dd").format(thisWeekDayStart[i])
            phoneUsedTimeMap.put(dateString, (totalTime / 1000 / 60).toInt())
            Log.e("getPhoneWeekUsedTime", "${totalTime / 1000 / 60}")
        }
        return phoneUsedTimeMap
    }

    fun getDayAppUsedTime(context: Context): Map<String, Long> {
        return getUsedTime(context, CalendarUtil.getTodayStartTime())
    }

    fun getWeekAppUsedTime(context: Context): Map<String, Long> {
        return getUsedTime(context, CalendarUtil.getWeekStartTime())
    }

    private fun getUsedTime(context: Context, startTime: Long): Map<String, Long> {
        val appMap = TreeMap<String, Long>()
        val mPackageManager = context.packageManager
        val queryUsageStates = mUsageStatsManager?.queryUsageStats(
            UsageStatsManager.INTERVAL_BEST,
            startTime,
            System.currentTimeMillis()
        )
        if (!queryUsageStates.isNullOrEmpty()) {
            for (item in queryUsageStates) {
                val app = mPackageManager!!.getApplicationInfo(item.packageName, 0)
                if (app.flags and ApplicationInfo.FLAG_SYSTEM != 0) {
                    continue
                }
                if (item.totalTimeInForeground / 1000 / 60 >= 10) {
                    appMap[app.loadLabel(mPackageManager).toString()] =
                        item.totalTimeInForeground / 1000 / 60
                }
            }
        }
        return if (appMap.size > 10) {
            appMap.toList().sortedByDescending { it.second }.slice(0..10).toMap()
        } else {
            appMap.toList().sortedByDescending { it.second }.toMap()
        }
    }
}