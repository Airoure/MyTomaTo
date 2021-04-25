package com.zjl.mytomato.entity

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "FinishTodoEntity", primaryKeys = ["finishDate", "finishTime"])
data class FinishTodoEntity(
        var name: String,
        var imageUrl: String = "",
        var finishDate: String = "",
        var finishTime: String = "",
        var hour: Int = 0,
        var minute: Int = 0
) : Parcelable
