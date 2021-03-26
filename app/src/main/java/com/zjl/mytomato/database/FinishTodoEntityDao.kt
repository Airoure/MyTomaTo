package com.zjl.mytomato.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.zjl.mytomato.entity.FinishTodoEntity
import com.zjl.mytomato.entity.FinishTodoType

@Dao
interface FinishTodoEntityDao {
    @Insert
    suspend fun insert(finishTodoEntity: FinishTodoEntity)

    @Delete
    suspend fun delete(finishTodoEntity: FinishTodoEntity)

    @Query("select * from FinishTodoEntity where finishDate = :date")
    suspend fun queryByData(date: String): List<FinishTodoEntity>

    @Query("select Count(*) from FinishTodoEntity")
    suspend fun getFinishTodoNum(): Int

    @Query("select sum(hour*60+minute) from FinishTodoEntity")
    suspend fun getTotalTime(): Int?

    @Query("select sum(hour*60+minute)/(select Count(distinct finishDate)) from FinishTodoEntity")
    suspend fun getAverageTime(): Int?

    @Query("select Count(*) from FinishTodoEntity where finishDate = :date")
    suspend fun getNumByDate(date: String): Int

    @Query("select sum(hour*60+minute) from FinishTodoEntity where finishDate = :date")
    suspend fun getTimeByDate(date: String): Int?

    @Query("select name,sum(hour*60+minute) as time from FinishTodoEntity where finishDate = :date group by name")
    suspend fun getFinishTodoByDate(date: String): List<FinishTodoType>
}