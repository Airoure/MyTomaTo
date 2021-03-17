package com.zjl.mytomato.worker

import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.getSystemService
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.zjl.mytomato.App
import java.text.SimpleDateFormat
import java.util.*


class ActivityGuard(context: Context, params: WorkerParameters) : Worker(context, params) {
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun doWork(): Result {
        while (App.isLocking){
            Thread.sleep(1000)
            val mUsageStatsManager =
                getSystemService(applicationContext, UsageStatsManager::class.java)
            val time = System.currentTimeMillis()
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.HOUR_OF_DAY,-1)
            val queryUsageStats =
                mUsageStatsManager?.queryUsageStats(UsageStatsManager.INTERVAL_BEST,0 , time)
            var recentTask: UsageStats? = null
            if (!queryUsageStats.isNullOrEmpty()) {
                for (item in queryUsageStats) {
                    //Log.e("123", "${item.packageName}+${SimpleDateFormat("HH:mm").format(item.totalTimeInForeground)}")
                    if (recentTask == null || recentTask.lastTimeUsed < item.lastTimeUsed) {
                        recentTask = item
                    }
                }
            }
            Log.e("999","${recentTask?.packageName}")
            if ("com.android.settings".equals(recentTask?.packageName)) {
                applicationContext.startActivity(
                    Intent().setAction("android.intent.action.MAIN")
                        .addCategory("android.intent.category.HOME")
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                )
            }
        }
        return Result.success()
    }
}