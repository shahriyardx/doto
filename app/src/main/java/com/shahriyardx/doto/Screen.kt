package com.shahriyardx.doto

sealed class Screen(val route: String) {
    object AddTodo: Screen(route="add_todo")
    object Todos: Screen(route="todos")
}