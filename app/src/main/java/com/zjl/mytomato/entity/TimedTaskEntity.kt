package com.zjl.mytomato.entity

import androidx.room.Entity


@Entity(tableName = "TimedTaskEntity")
data class TimedTaskEntity(
        var name: String,
        var hour: Int,
        var minute: Int,
        var second: Int = 0,
        var imageUrl: String = "",
        var startHour: Int,
        var startMinute: Int
)