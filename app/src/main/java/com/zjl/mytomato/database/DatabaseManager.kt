package com.zjl.mytomato.database

import android.content.Context
import androidx.room.Room
import com.zjl.mytomato.entity.FinishTodoEntity
import com.zjl.mytomato.entity.FinishTodoType
import com.zjl.mytomato.entity.TimedTaskEntity
import com.zjl.mytomato.entity.TodoEntity
import com.zjl.mytomato.util.SpUtil

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

    private val finishTodoEntityDao by lazy { database.getFinishTodoEntityDao() }

    private val timedTaskEntityDao by lazy { database.getTimedTaskEntityDao() }


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
        return todoEntityDao.queryByName(name, SpUtil.getUsername())
    }

    suspend fun queryTodoEntityAll(): MutableList<TodoEntity> {
        return todoEntityDao.queryAll(SpUtil.getUsername())
    }

    suspend fun queryTodoEntityAllInSet(): MutableSet<TodoEntity> {
        return todoEntityDao.queryAll(SpUtil.getUsername()).toMutableSet()
    }

    suspend fun insertFinishTodoEntity(finishTodoEntity: FinishTodoEntity) {
        finishTodoEntityDao.insert(finishTodoEntity)
    }

    suspend fun deleteFinishTodoEntity(finishTodoEntity: FinishTodoEntity) {
        finishTodoEntityDao.delete(finishTodoEntity)
    }

    suspend fun queryFinishTodoEntityByDate(date: String): List<FinishTodoEntity> {
        return finishTodoEntityDao.queryByData(date, SpUtil.getUsername())
    }

    suspend fun getFinishTodoNum(): Int {
        return finishTodoEntityDao.getFinishTodoNum(SpUtil.getUsername())
    }

    suspend fun getTotalTime(): Int? {
        return finishTodoEntityDao.getTotalTime(SpUtil.getUsername())
    }

    suspend fun getAverageTime(): Int? {
        return finishTodoEntityDao.getAverageTime(SpUtil.getUsername())
    }

    suspend fun getNumByDate(date: String): Int {
        return finishTodoEntityDao.getNumByDate(date, SpUtil.getUsername())
    }

    suspend fun getTimeByDate(date: String): Int? {
        return finishTodoEntityDao.getTimeByDate(date, SpUtil.getUsername())
    }

    suspend fun getFinishTodoByDate(date: String): List<FinishTodoType> {
        return finishTodoEntityDao.getFinishTodoByDate(date, SpUtil.getUsername())
    }

    suspend fun insertTimedTaskEntity(timedTaskEntity: TimedTaskEntity): Long {
        return timedTaskEntityDao.addTimedTaskEntity(timedTaskEntity)
    }

    suspend fun queryTimedTaskEntityAll(): List<TimedTaskEntity> {
        return timedTaskEntityDao.queryTimedTaskEntityAll(SpUtil.getUsername())
    }

    suspend fun changeTimedTaskEnable(timedTaskEntity: TimedTaskEntity) {
        return timedTaskEntityDao.changeTimedTaskEnable(timedTaskEntity)
    }

    suspend fun deleteTimeTaskEntity(timedTaskEntity: TimedTaskEntity) {
        timedTaskEntityDao.deleteTimeTaskEntity(timedTaskEntity)
    }

    suspend fun getFocusTimeByDate(allThisWeekDay: List<String>): Map<String, Int> {
        val resMap = mutableMapOf<String, Int>()
        for (item in allThisWeekDay) {
            val value = finishTodoEntityDao.getTimeByDate(item, SpUtil.getUsername())
            resMap[item.substring(5).replace("月", "-").replace("日", "")] = value ?: 0
        }
        return resMap
    }

    suspend fun queryFinishTodoEntityAll(): MutableSet<FinishTodoEntity> {
        return finishTodoEntityDao.queryFinishTodoEntityAll(SpUtil.getUsername()).toMutableSet()
    }


}