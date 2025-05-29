package com.shahriyardx.doto.repositories

import com.shahriyardx.doto.database.Database
import com.shahriyardx.doto.database.todo.TodoEntity
import com.shahriyardx.doto.viewmodels.todo.TodoFilter
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    val database: Database

    fun getTodos(filter: TodoFilter): Flow<List<TodoEntity>>
    suspend fun addTodo(todo: TodoEntity): Long
    suspend fun updateTodo(todo: TodoEntity)
    suspend fun deleteTodo(todo: TodoEntity)
}