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
import androidx.navigation.toRoute
import com.shahriyardx.doto.screens.todo.todo_details.DetailsPage
import com.shahriyardx.doto.screens.todo.add_todo.AddTodoPage
import com.shahriyardx.doto.screens.todo.todo_list.TodoListPage

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
        NavHost(navController = navController, startDestination = TodosScreen) {
            composable<TodosScreen>(
                enterTransition = { enterTransition },
                exitTransition = { exitTransition }
            ) {
                TodoListPage()
            }

            composable<AddTodoScreen>(
                enterTransition = { enterTransition },
                exitTransition = { exitTransition }
            ) {
                AddTodoPage()
            }

            composable<DetailsScreen>() {
                val args = it.toRoute<DetailsScreen>()
                DetailsPage(args.id)
            }
        }
    }
}