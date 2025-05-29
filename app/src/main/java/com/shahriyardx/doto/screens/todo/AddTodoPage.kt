package com.shahriyardx.doto.screens.todo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.shahriyardx.doto.screens.layouts.AppLayout

@Composable
fun AddTodoPage() {
    AppLayout {
        TodoForm()
    }
}