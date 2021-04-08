package com.zjl.mytomato.database

import androidx.room.*
import com.zjl.mytomato.entity.TimedTaskEntity

@Dao
interface TimedTaskEntityDao {

    @Insert
    suspend fun addTimedTaskEntity(timedTaskEntity: TimedTaskEntity): Long

    @Query("select * from TimedTaskEntity")
    suspend fun queryTimedTaskEntityAll(): List<TimedTaskEntity>

    @Update(entity = TimedTaskEntity::class)
    suspend fun changeTimedTaskEnable(timedTaskEntity: TimedTaskEntity)

    @Delete
    suspend fun deleteTimeTaskEntity(timedTaskEntity: TimedTaskEntity)
}