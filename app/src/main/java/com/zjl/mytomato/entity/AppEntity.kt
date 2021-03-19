package com.zjl.mytomato.entity

import android.graphics.drawable.Drawable
import androidx.room.Entity

@Entity(tableName = "AppEntity")
data class AppEntity(
        var name: String,
        var icon: Drawable
)
