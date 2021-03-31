package com.zjl.mytomato.util

import java.util.*

object CalendarUtil {
    fun getTodayStartTime(): Long {
        val instance = Calendar.getInstance()
        instance.set(Calendar.HOUR_OF_DAY, 0)
        instance.set(Calendar.MINUTE, 0)
        instance.set(Calendar.SECOND, 0)
        instance.set(Calendar.MILLISECOND, 0)
        return instance.timeInMillis
    }

    fun getTodayEndTime(): Long {
        val instance = Calendar.getInstance()
        instance.set(Calendar.HOUR_OF_DAY, 23)
        instance.set(Calendar.MINUTE, 59)
        instance.set(Calendar.SECOND, 59)
        instance.set(Calendar.MILLISECOND, 999)
        return instance.timeInMillis
    }

    fun getWeekStartTime(): Long {
        val instance = Calendar.getInstance()
        instance.firstDayOfWeek = Calendar.MONDAY
        instance.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
        instance.set(Calendar.HOUR_OF_DAY, 0)
        instance.set(Calendar.MINUTE, 0)
        instance.set(Calendar.SECOND, 0)
        instance.set(Calendar.MILLISECOND, 0)
        return instance.timeInMillis
    }

    fun getWeekEndTime(): Long {
        val instance = Calendar.getInstance()
        instance.firstDayOfWeek = Calendar.MONDAY
        instance.time = Date(getWeekStartTime())
        instance.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
        instance.set(Calendar.HOUR_OF_DAY, 23)
        instance.set(Calendar.MINUTE, 59)
        instance.set(Calendar.SECOND, 59)
        instance.set(Calendar.MILLISECOND, 999)
        return instance.timeInMillis
    }

    fun getMonthStartTime(): Long {
        val instance = Calendar.getInstance()
        instance.time = Date(getWeekStartTime())
        instance.set(Calendar.DAY_OF_MONTH, 1)
        instance.set(Calendar.HOUR_OF_DAY, 23)
        instance.set(Calendar.MINUTE, 59)
        instance.set(Calendar.SECOND, 59)
        instance.set(Calendar.MILLISECOND, 999)
        return instance.timeInMillis
    }

    fun getMonthEndTime(): Long {
        val instance = Calendar.getInstance()
        instance.time = Date(getWeekStartTime())
        instance.set(Calendar.DAY_OF_MONTH, instance.getActualMaximum(Calendar.DAY_OF_MONTH))
        instance.set(Calendar.HOUR_OF_DAY, 23)
        instance.set(Calendar.MINUTE, 59)
        instance.set(Calendar.SECOND, 59)
        instance.set(Calendar.MILLISECOND, 999)
        return instance.timeInMillis
    }
}