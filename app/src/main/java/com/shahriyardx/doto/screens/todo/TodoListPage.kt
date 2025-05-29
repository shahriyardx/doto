package com.shahriyardx.doto.screens.todo

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.shahriyardx.doto.screens.layouts.AppLayout

@Composable
fun TodoListPage() {
    AppLayout(showActionButton = true) {
        TodoList(modifier = Modifier)
    }
}