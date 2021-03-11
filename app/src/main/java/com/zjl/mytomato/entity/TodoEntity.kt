package com.zjl.mytomato.entity

import androidx.room.Entity

@Entity(tableName = "TodoEntity",primaryKeys = ["name"])
data class TodoEntity(
    var name: String,
    var hour: Int,
    var minute: Int
)