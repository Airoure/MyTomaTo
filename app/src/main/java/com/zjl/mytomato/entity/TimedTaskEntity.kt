package com.zjl.mytomato.entity

import android.os.Parcelable
import androidx.room.Entity
import cn.bmob.v3.BmobObject
import com.zjl.mytomato.util.SpUtil
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(
        tableName = "TimedTaskEntity",
        primaryKeys = ["name", "startHour", "startMinute", "username"]
)
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
        val requestCode: Long = System.currentTimeMillis(),
        var username: String = SpUtil.getUsername()
) : Parcelable, BaseLocalEntity {
    fun toTodoEntity(): TodoEntity {
        return TodoEntity(
                name,
                hour,
                minute,
                second,
                imageUrl
        )
    }


    override fun toNetwork(username: String) = NTimedTaskEntity(
            name,
            hour,
            minute,
            second,
            imageUrl,
            startHour,
            startMinute,
            enable,
            isMonday,
            isTuesday,
            isWednesday,
            isThursday,
            isFriday,
            isSaturday,
            isSunday,
            requestCode,
            username
    )

}

class NTimedTaskEntity(
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
        val requestCode: Long = System.currentTimeMillis(),
        val username: String
) : BmobObject(), BaseNetworkEntity {

    override fun toLocal() = TimedTaskEntity(
            name,
            hour,
            minute,
            second,
            imageUrl,
            startHour,
            startMinute,
            enable,
            isMonday,
            isTuesday,
            isWednesday,
            isThursday,
            isFriday,
            isSaturday,
            isSunday,
            requestCode,
            username
    )

    companion object {
        fun getInstance() = NTimedTaskEntity(
                hour = 0,
                minute = 0,
                startHour = 0,
                startMinute = 0,
                name = "",
                username = ""
        )
    }
}