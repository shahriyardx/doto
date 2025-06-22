package com.shahriyardx.doto.screens.todo.todo_list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shahriyardx.doto.DetailsScreen
import com.shahriyardx.doto.LocalNavController
import com.shahriyardx.doto.viewmodels.todo.TodoAction
import com.shahriyardx.doto.viewmodels.todo.TodoFilter
import com.shahriyardx.doto.viewmodels.todo.TodoViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoByFilter(filter: TodoFilter) {
    val viewModel = koinViewModel<TodoViewModel>()
    val state by viewModel.state.collectAsState()
    val sheetState = rememberModalBottomSheetState()

    val navController = LocalNavController.current

    val todos = state.todos.filter { todo ->
        when (filter) {
            TodoFilter.ALL -> true
            TodoFilter.INCOMPLETE -> !todo.isComplete
            TodoFilter.COMPLETED -> todo.isComplete
        }
    }

    val isDark = isSystemInDarkTheme()

    when {
        todos.isNotEmpty() -> LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(todos) { todo ->
                val showBottomSheet = remember {
                    mutableStateOf(false)
                }

                SwipeableTodoWithAction(modifier = Modifier.fillMaxWidth(), actions = {
                    ActionIcon(
                        modifier = Modifier
                            .fillMaxHeight()
                            .background(Color.Red),
                        icon = Icons.Default.Delete,
                        tint = Color.White,
                        onClick = {
                            viewModel.onEvent(TodoAction.Delete(todo))
                        })

                    ActionIcon(
                        modifier = Modifier
                            .fillMaxHeight()
                            .background(Color.Blue),
                        icon = Icons.Default.Check,
                        tint = Color.White,
                        onClick = {
                            viewModel.onEvent(TodoAction.Completed(todo))
                        })
                }, isRevealed = false, onDelete = {
                    viewModel.onEvent(TodoAction.Delete(todo))
                }) {
                    Row(
                        modifier = Modifier
                            .clickable(onClick = {
                                showBottomSheet.value = true
                            })
                            .padding(10.dp)

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

                if (showBottomSheet.value) {
                    ModalBottomSheet(
                        onDismissRequest = { showBottomSheet.value = false },
                        sheetState = sheetState
                    ) {
                        // Update Form
                    }
                }
            }
        }

        else -> Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Text("Nothing to show")
        }
    }

}