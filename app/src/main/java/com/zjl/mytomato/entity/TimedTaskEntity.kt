package com.zjl.mytomato.entity

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "TimedTaskEntity", primaryKeys = ["name", "startHour", "startMinute"])
data class TimedTaskEntity(
        var name: String,
        var hour: Int,
        var minute: Int,
        var second: Int = 0,
        var imageUrl: String = "",
        var startHour: Int,
        var startMinute: Int,
        var enable: Boolean = true,
        var isMonday: Boolean = false,
        var isTuesday: Boolean = false,
        var isWednesday: Boolean = false,
        var isThursday: Boolean = false,
        var isFriday: Boolean = false,
        var isSaturday: Boolean = false,
        var isSunday: Boolean = false,
        val requestCode: Long = System.currentTimeMillis()
) : Parcelable {
    fun toTodoEntity(): TodoEntity {
        return TodoEntity(
                name,
                hour,
                minute,
                second,
                imageUrl
        )
    }
}