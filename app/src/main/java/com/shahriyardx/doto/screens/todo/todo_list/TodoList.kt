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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoList(modifier: Modifier) {
    val navController = LocalNavController.current
    val viewModel = koinViewModel<TodoViewModel>()
    val state by viewModel.state.collectAsState()

    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember {
        mutableStateOf(false)
    }

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

                if (showBottomSheet) {
                    ModalBottomSheet(
                        onDismissRequest = { showBottomSheet = false },
                        sheetState = sheetState
                    ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "Todo Action",
                                fontSize = 25.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Row(
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Button(onClick = {
                                    viewModel.onEvent(TodoAction.Completed(todo))
                                }, modifier = Modifier.weight(1f)) {
                                    Text(text = "Mark as ${if (todo.isComplete) "incomplete" else "complete"}")
                                }
                                Button(
                                    onClick = {
                                        viewModel.onEvent(TodoAction.Delete(todo))
                                    },
                                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.error),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(text = "Delete")
                                }
                            }
                        }
                    }
                }

                Row(
                    modifier = Modifier
                        .background(
                            backgroundColor, shape = RoundedCornerShape(8.dp)
                        )
                        .padding(10.dp)
                        .clickable(onClick = {
                            showBottomSheet = true
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

                        IconButton(onClick = {
                            navController.navigate(DetailsScreen(todo.id))
                        }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = "Delete Icon"
                            )
                        }
                    }
                }
            }
        }
    }
}