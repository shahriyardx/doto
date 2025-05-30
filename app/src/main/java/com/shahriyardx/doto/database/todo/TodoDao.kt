package com.shahriyardx.doto.database.todo

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    @Insert
    suspend fun insert(todo: TodoEntity): Long

    @Update
    suspend fun update(todo: TodoEntity)

    @Delete
    suspend fun delete(todo: TodoEntity)

    @Query("SELECT * FROM TodoEntity")
    fun getAllTodos(): Flow<List<TodoEntity>>

    @Query("SELECT * FROM TodoEntity WHERE isComplete = 1")
    fun getCompletedTodos(): Flow<List<TodoEntity>>

    @Query("SELECT * FROM TodoEntity WHERE isComplete = 0")
    fun getIncompleteTodos(): Flow<List<TodoEntity>>

    @Query("SELECT * FROM TodoEntity WHERE id = :id")
    fun getTodoById(id: Int): Flow<TodoEntity?>
}