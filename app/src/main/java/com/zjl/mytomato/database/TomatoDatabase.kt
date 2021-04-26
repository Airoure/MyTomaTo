package com.zjl.mytomato.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zjl.mytomato.entity.FinishTodoEntity
import com.zjl.mytomato.entity.TimedTaskEntity
import com.zjl.mytomato.entity.TodoEntity

@Database(
        entities = [TodoEntity::class, FinishTodoEntity::class, TimedTaskEntity::class],
        version = 1,
        exportSchema = false
)
abstract class TomatoDatabase : RoomDatabase() {
    abstract fun getTodoEntityDao(): TodoEntityDao
    abstract fun getFinishTodoEntityDao(): FinishTodoEntityDao
    abstract fun getTimedTaskEntityDao(): TimedTaskEntityDao
}