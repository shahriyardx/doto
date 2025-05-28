package com.shahriyardx.doto

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TodoList(modifier: Modifier) {
    val viewModel = LocalViewModelComposition.current
    val state by viewModel.state.collectAsState()

    Column(modifier = modifier) {
        Text(
            text = "Todos",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 10.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 5.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            state.todos.forEach { todo ->
                Row(modifier = Modifier
                    .background(Color.LightGray)
                    .padding(10.dp)) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(todo.title, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Text(todo.description, color = Color.Gray)
                    }

                    Row {
                        Checkbox(checked = todo.isComplete, onCheckedChange = {
                            viewModel.onEvent(TodoAction.Completed(todo))
                        })
                        IconButton(onClick = {
                            viewModel.onEvent(TodoAction.Delete(todo))
                        }) {
                            Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete Icon")
                        }
                    }
                }
            }
        }
    }
}