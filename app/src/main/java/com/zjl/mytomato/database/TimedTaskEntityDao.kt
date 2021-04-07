package com.zjl.mytomato.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.zjl.mytomato.entity.TimedTaskEntity

@Dao
interface TimedTaskEntityDao {

    @Insert
    suspend fun addTimedTaskEntity(timedTaskEntity: TimedTaskEntity) : Long

    @Query("select * from TimedTaskEntity")
    suspend fun queryTimedTaskEntityAll(): List<TimedTaskEntity>

    @Update(entity = TimedTaskEntity::class)
    suspend fun changeTimedTaskEnable(timedTaskEntity: TimedTaskEntity)
}