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

    @Query("select * from FinishTodoEntity where finishDate = :date and username = :username")
    suspend fun queryByData(date: String, username: String): List<FinishTodoEntity>

    @Query("select Count(*) from FinishTodoEntity where username = :username")
    suspend fun getFinishTodoNum(username: String): Int

    @Query("select sum(hour*60+minute) from FinishTodoEntity where username = :username")
    suspend fun getTotalTime(username: String): Int?

    @Query("select sum(hour*60+minute)/(select Count(distinct finishDate)) from FinishTodoEntity where username = :username")
    suspend fun getAverageTime(username: String): Int?

    @Query("select Count(*) from FinishTodoEntity where finishDate = :date and username = :username")
    suspend fun getNumByDate(date: String, username: String): Int

    @Query("select sum(hour*60+minute) from FinishTodoEntity where finishDate = :date and username = :username")
    suspend fun getTimeByDate(date: String, username: String): Int?

    @Query("select name,sum(hour*60+minute) as time from FinishTodoEntity where finishDate = :date and username = :username group by name")
    suspend fun getFinishTodoByDate(date: String, username: String): List<FinishTodoType>

    @Query("select * from FinishTodoEntity where username = :username")
    suspend fun queryFinishTodoEntityAll(username: String): List<FinishTodoEntity>

}