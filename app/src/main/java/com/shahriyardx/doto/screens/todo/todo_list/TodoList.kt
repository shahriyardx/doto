package com.shahriyardx.doto.screens.todo.todo_list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.shahriyardx.doto.DetailsScreen
import com.shahriyardx.doto.LocalNavController
import com.shahriyardx.doto.viewmodels.todo.TodoAction
import com.shahriyardx.doto.viewmodels.todo.TodoViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun TodoList(modifier: Modifier) {
    val navController = LocalNavController.current
    val viewModel = koinViewModel<TodoViewModel>()
    val state by viewModel.state.collectAsState()

    Column(modifier = modifier) {
        Text(
            text = "Todos",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 5.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            state.todos.forEach { todo ->
                val isDark = isSystemInDarkTheme()
                val backgroundColor = if (isDark) Color.DarkGray else Color.LightGray

                Row(
                    modifier = Modifier
                        .background(backgroundColor, shape = RoundedCornerShape(8.dp))
                        .padding(10.dp)
                        .clickable(onClick = {
                            navController.navigate(DetailsScreen(todo.id))
                        })
                ) {

                    val titleColor = if (isDark) Color.White else Color.Black
                    val descriptionColor = if (isDark) Color.LightGray else Color.DarkGray

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            todo.title,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = titleColor
                        )
                        Text(
                            todo.description, color = descriptionColor
                        )
                    }

                    Row {
                        Checkbox(checked = todo.isComplete, onCheckedChange = {
                            viewModel.onEvent(TodoAction.Completed(todo))
                        })
                        IconButton(onClick = {
                            viewModel.onEvent(TodoAction.Delete(todo))
                        }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete Icon"
                            )
                        }
                    }
                }
            }
        }
    }
}