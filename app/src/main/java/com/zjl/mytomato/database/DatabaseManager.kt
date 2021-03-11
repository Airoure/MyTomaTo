package com.zjl.mytomato.database

import android.content.Context
import androidx.room.Room
import com.zjl.mytomato.entity.TodoEntity

const val DATABASE_NAME = "Tomato.db"

class DatabaseManager private constructor() {

    companion object {
        private var INSTANSE: DatabaseManager? = null
        private var appContext: Context? = null

        fun init(context: Context) {
            appContext = context
        }

        fun get(): DatabaseManager {
            if (INSTANSE == null) {
                INSTANSE = DatabaseManager()
            }
            return INSTANSE!!
        }
    }

    private val database by lazy {
        Room.databaseBuilder(
            appContext!!,
            TomatoDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    private val todoEntityDao by lazy { database.getTodoEntityDao() }

    suspend fun insertTodoEntity(todoEntity: TodoEntity): Long {
        return todoEntityDao.insert(todoEntity)
    }

    suspend fun deleteTodoEntity(todoEntity: TodoEntity): Int {
        return todoEntityDao.delete(todoEntity)
    }

    suspend fun updateTodoEntity(todoEntity: TodoEntity): Int {
        return todoEntityDao.update(todoEntity)
    }

    suspend fun queryTodoEntityByName(name: String): List<TodoEntity> {
        return todoEntityDao.queryByName(name)
    }

    suspend fun queryTodoEntityAll(): MutableList<TodoEntity> {
        return todoEntityDao.queryAll()
    }
}