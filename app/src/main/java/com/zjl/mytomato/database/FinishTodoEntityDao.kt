package com.zjl.mytomato.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.zjl.mytomato.entity.FinishTodoEntity

@Dao
interface FinishTodoEntityDao {
    @Insert
    suspend fun insert(finishTodoEntity: FinishTodoEntity)

    @Delete
    suspend fun delete(finishTodoEntity: FinishTodoEntity)

    @Query("select * from FinishTodoEntity where finishDate = :date")
    suspend fun queryByData(date: String): List<FinishTodoEntity>
}