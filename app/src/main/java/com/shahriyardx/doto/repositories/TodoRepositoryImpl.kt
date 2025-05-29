package com.shahriyardx.doto.repositories

import com.shahriyardx.doto.database.Database
import com.shahriyardx.doto.database.todo.TodoDao
import com.shahriyardx.doto.database.todo.TodoEntity
import com.shahriyardx.doto.viewmodels.todo.TodoFilter
import kotlinx.coroutines.flow.Flow

class TodoRepositoryImpl(private val database: Database): TodoRepository {
    private val dao = database.dao

    override fun getTodos(filter: TodoFilter): Flow<List<TodoEntity>> {
        return when (filter) {
            TodoFilter.ALL -> dao.getAllTodos()
            TodoFilter.COMPLETED -> dao.getCompletedTodos()
            TodoFilter.INCOMPLETE -> dao.getIncompleteTodos()
        }
    }

    override suspend fun addTodo(todo: TodoEntity): Long {
        return dao.insert(todo)
    }

    override suspend fun updateTodo(todo: TodoEntity) {
        dao.update(todo)
    }

    override suspend fun deleteTodo(todo: TodoEntity) {
        dao.delete(todo)
    }
}