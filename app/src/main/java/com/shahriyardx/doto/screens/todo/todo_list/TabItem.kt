package com.shahriyardx.doto.screens.todo.todo_list

import androidx.compose.ui.graphics.vector.ImageVector
import com.shahriyardx.doto.viewmodels.todo.TodoFilter

data class TabItem(
    val title: String,
    val unselectedIcon: ImageVector,
    val selectedIcon: ImageVector,
    val filterType: TodoFilter
)