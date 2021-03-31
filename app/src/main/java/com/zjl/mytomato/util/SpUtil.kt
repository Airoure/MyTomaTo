package com.zjl.mytomato.util

import android.content.Context
import androidx.core.content.edit
import com.zjl.mytomato.App
import java.util.*

object SpUtil {
    private const val SP_NAME = "mytomato_sp"

    private val sp = App.appContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)

    fun setLockTimes(lockTimes: Int) {
        sp.edit {
            putInt("lock_time", lockTimes)
        }
    }

    fun getLockTimes() = sp.getInt("lock_time", 0)

    fun setLockMonth() {
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        val month = calendar.get(Calendar.MONTH)
        sp.edit {
            putInt("lock_month", month)
        }
    }

    fun getLockMonth() = sp.getInt("lock_month", 0)

    fun setWorkTime(workTime: Long) {
        sp.edit {
            putLong("work_time", workTime * 60 * 1000)
        }
    }

    fun getWorkTime() = sp.getLong("work_time", 3 * 10 * 1000)

    fun setRestTime(restTime: Long) {
        sp.edit {
            putLong("rest_time", restTime * 60 * 1000)
        }
    }

    fun getRestTime() = sp.getLong("rest_time", 1 * 10 * 1000)

}