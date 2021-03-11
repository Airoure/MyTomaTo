package com.zjl.mytomato.database

import androidx.room.*
import com.zjl.mytomato.entity.TodoEntity

@Dao
interface TodoEntityDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(todoEntity: TodoEntity): Long

    @Delete
    suspend fun delete(todoEntity: TodoEntity): Int

    @Update
    suspend fun update(todoEntity: TodoEntity): Int

    @Query("select * from TodoEntity where name = :name")
    suspend fun queryByName(name:String): List<TodoEntity>

    @Query("select * from TodoEntity")
    suspend fun queryAll(): MutableList<TodoEntity>
}