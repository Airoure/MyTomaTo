package com.zjl.mytomato.entity

import android.os.Parcelable
import androidx.room.Entity
import cn.bmob.v3.BmobObject
import com.zjl.mytomato.util.SpUtil
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "TodoEntity", primaryKeys = ["name", "username"])
data class TodoEntity(
    var name: String,
    var hour: Int,
    var minute: Int,
    var second: Int = 0,
    var imageUrl: String = "",
    var username: String = SpUtil.getUsername()
) : Parcelable, BaseLocalEntity {
    override fun toNetwork(username: String) = NetworkTodoEntity(
        this.name,
        this.hour,
        this.minute,
        this.second,
        this.imageUrl,
        username
    )

    override fun equals(other: Any?): Boolean {
        if (other is TodoEntity) {
            return this.name.equals(other.name)
        }
        return false

    }
}

class NetworkTodoEntity(
    var name: String = "",
    var hour: Int = 0,
    var minute: Int = 0,
    var second: Int = 0,
    var imageUrl: String = "",
    var username: String = ""
) : BmobObject(), BaseNetworkEntity {
    override fun toLocal() = TodoEntity(
        this.name,
        this.hour,
        this.minute,
        this.second,
        this.imageUrl,
        this.username
    )

    override fun equals(other: Any?): Boolean {
        if (other is NetworkTodoEntity) {
            return this.name.equals(other.name) && this.username.equals(other.username)
        }
        return false

    }
}