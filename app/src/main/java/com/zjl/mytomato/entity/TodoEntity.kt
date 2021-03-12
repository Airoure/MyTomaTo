package com.zjl.mytomato.entity

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "TodoEntity",primaryKeys = ["name"])
data class TodoEntity(
    var name: String,
    var hour: Int,
    var minute: Int,
    var second:Int = 0,
    var imageUrl: String = ""
) : Parcelable