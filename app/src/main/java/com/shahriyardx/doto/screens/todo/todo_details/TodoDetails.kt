package com.shahriyardx.doto.screens.todo.todo_details

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.shahriyardx.doto.screens.layouts.AppLayout

@Composable
fun DetailsPage(id: Int) {
    AppLayout() {
        Text(text="Id: $id")
    }
}