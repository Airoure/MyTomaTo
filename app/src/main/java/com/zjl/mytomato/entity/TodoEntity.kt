package com.zjl.mytomato.entity

import android.os.Parcelable
import androidx.room.Entity
import cn.bmob.v3.BmobObject
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "TodoEntity", primaryKeys = ["name"])
data class TodoEntity(
        var name: String,
        var hour: Int,
        var minute: Int,
        var second: Int = 0,
        var imageUrl: String = ""
) : Parcelable {
    fun toNetwork(userName: String) = NetworkTodoEntity(
            this.name,
            this.hour,
            this.minute,
            this.second,
            this.imageUrl,
            userName
    )
}

class NetworkTodoEntity(
        var name: String,
        var hour: Int,
        var minute: Int,
        var second: Int = 0,
        var imageUrl: String = "",
        var userName: String
) : BmobObject() {
    fun toLocal() = TodoEntity(
            this.name,
            this.hour,
            this.minute,
            this.second,
            this.imageUrl
    )
}