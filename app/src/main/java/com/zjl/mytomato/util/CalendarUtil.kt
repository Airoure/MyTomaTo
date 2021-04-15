package com.zjl.mytomato.util

import java.text.SimpleDateFormat
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

    fun getTimeMilled(weekDay: Int, hour: Int, minute: Int): Long {
        val now = Calendar.getInstance()
        val nowMills = now.timeInMillis
        val instance = Calendar.getInstance()
        instance.set(Calendar.DAY_OF_WEEK, weekDay)
        instance.set(Calendar.HOUR_OF_DAY, hour)
        instance.set(Calendar.MINUTE, minute)
        instance.set(Calendar.SECOND, 0)
        if (instance.timeInMillis < nowMills) {
            //如果是过去时间，就设定为下一周
            instance.add(Calendar.DATE, 7)
        }
        return instance.timeInMillis
    }

    fun getAllThisWeekDayStart(): List<Long> {
        val list = mutableListOf<Long>()
        val instance = Calendar.getInstance()
        instance.firstDayOfWeek = Calendar.MONDAY
        instance.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
        instance.set(Calendar.HOUR_OF_DAY, 0)
        instance.set(Calendar.MINUTE, 0)
        instance.set(Calendar.SECOND, 0)
        instance.set(Calendar.MILLISECOND, 0)
        list.add(instance.timeInMillis)
        instance.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY)
        list.add(instance.timeInMillis)
        instance.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY)
        list.add(instance.timeInMillis)
        instance.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY)
        list.add(instance.timeInMillis)
        instance.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY)
        list.add(instance.timeInMillis)
        instance.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY)
        list.add(instance.timeInMillis)
        instance.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
        list.add(instance.timeInMillis)
        return list
    }

    fun getAllThisWeekDayEnd(): List<Long> {
        val list = mutableListOf<Long>()
        val instance = Calendar.getInstance()
        instance.firstDayOfWeek = Calendar.MONDAY
        instance.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
        instance.set(Calendar.HOUR_OF_DAY, 23)
        instance.set(Calendar.MINUTE, 59)
        instance.set(Calendar.SECOND, 59)
        instance.set(Calendar.MILLISECOND, 999)
        list.add(instance.timeInMillis)
        instance.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY)
        list.add(instance.timeInMillis)
        instance.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY)
        list.add(instance.timeInMillis)
        instance.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY)
        list.add(instance.timeInMillis)
        instance.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY)
        list.add(instance.timeInMillis)
        instance.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY)
        list.add(instance.timeInMillis)
        instance.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
        list.add(instance.timeInMillis)
        return list
    }

    fun getAllThisWeekDay(): List<String> {
        val list = mutableListOf<String>()
        val instance = Calendar.getInstance()
        instance.firstDayOfWeek = Calendar.MONDAY
        instance.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)

        list.add(SimpleDateFormat("yyyy年MM月dd日").format(instance.timeInMillis))
        instance.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY)
        list.add(SimpleDateFormat("yyyy年MM月dd日").format(instance.timeInMillis))
        instance.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY)
        list.add(SimpleDateFormat("yyyy年MM月dd日").format(instance.timeInMillis))
        instance.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY)
        list.add(SimpleDateFormat("yyyy年MM月dd日").format(instance.timeInMillis))
        instance.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY)
        list.add(SimpleDateFormat("yyyy年MM月dd日").format(instance.timeInMillis))
        instance.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY)
        list.add(SimpleDateFormat("yyyy年MM月dd日").format(instance.timeInMillis))
        instance.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
        list.add(SimpleDateFormat("yyyy年MM月dd日").format(instance.timeInMillis))
        return list
    }

    fun getWhatDay(): Int {
        val instance = Calendar.getInstance()
        return instance.get(Calendar.DAY_OF_WEEK)
    }
}