package com.shahriyardx.doto

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
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

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Navigation() {
    val navController = rememberNavController()
    val enterTransition = slideInHorizontally(
        initialOffsetX = { fullWidth -> -fullWidth }, animationSpec = tween(300)
    ) + fadeIn(animationSpec = tween(300))


    val exitTransition = slideOutHorizontally(
        animationSpec = tween(300)
    ) + fadeOut(animationSpec = tween(300))

    CompositionLocalProvider(LocalNavController provides navController) {
        NavHost(navController = navController, startDestination = Screen.Todos.route) {
            composable(route = Screen.Todos.route, enterTransition = {
                enterTransition
            }, exitTransition = {
                exitTransition
            }) {
                TodoListPage()
            }

            composable(route = Screen.AddTodo.route, enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { fullWidth -> -fullWidth }, animationSpec = tween(300)
                ) + fadeIn(animationSpec = tween(300))
            }, exitTransition = {
                slideOutHorizontally(
                    animationSpec = tween(300)
                ) + fadeOut(animationSpec = tween(300))
            }) {
                AddTodoPage()
            }
        }
    }
}