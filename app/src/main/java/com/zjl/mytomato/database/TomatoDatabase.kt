package com.zjl.mytomato.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zjl.mytomato.entity.TodoEntity

@Database(entities = [TodoEntity::class],version = 1,exportSchema = false)
abstract class TomatoDatabase :RoomDatabase() {
    abstract fun getTodoEntityDao():TodoEntityDao
}