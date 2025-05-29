package com.shahriyardx.doto.viewmodels.todo

import com.shahriyardx.doto.viewmodels.todo.TodoFilter
import com.shahriyardx.doto.database.todo.TodoEntity

data class TodoState(
    val title: String = "",
    val description: String = "",
    val todos: List<TodoEntity> = emptyList(),
    val filterType: TodoFilter = TodoFilter.ALL,
)