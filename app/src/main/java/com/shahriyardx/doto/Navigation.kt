package com.shahriyardx.doto

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shahriyardx.doto.screens.todo.AddTodoPage
import com.shahriyardx.doto.screens.todo.TodoListPage

val LocalNavController = staticCompositionLocalOf<NavHostController> {
    error("No LocalNavController provided")
}

@Composable
fun Navigation() {
    val navController = rememberNavController()

    CompositionLocalProvider(LocalNavController provides navController) {
        NavHost(navController = navController, startDestination = Screen.Todos.route) {
            composable(route = Screen.Todos.route) {
                TodoListPage()
            }

            composable(route = Screen.AddTodo.route) {
                AddTodoPage()
            }
        }
    }
}