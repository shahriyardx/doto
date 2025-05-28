package com.shahriyardx.doto

import com.shahriyardx.doto.database.todo.TodoEntity

data class TodoState(
    val title: String = "",
    val description: String = "",
    val todos: List<TodoEntity> = emptyList(),
    val filterType: TodoFilter = TodoFilter.ALL,
)