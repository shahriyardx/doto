package com.shahriyardx.doto.screens.layouts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shahriyardx.doto.screens.todo.add_todo.TodoFormInputs
import com.shahriyardx.doto.viewmodels.todo.TodoAction
import com.shahriyardx.doto.viewmodels.todo.TodoViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppLayout(
    modifier: Modifier = Modifier,
    showActionButton: Boolean = false,
    content: @Composable () -> Unit
) {
    val viewModel = koinViewModel<TodoViewModel>()
    val dialogOpen = remember { mutableStateOf(false) }

    when {
        dialogOpen.value -> {
            AlertDialog(
                onDismissRequest = { dialogOpen.value = false },
                title = {
                    Text("Add New Todo")
                },
                confirmButton = {
                    Button(onClick = {
                        viewModel.onEvent(TodoAction.Add, onFinish = {
                            dialogOpen.value = false
                        })
                    }) {
                        Text("Add To Database")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { dialogOpen.value = false }) {
                        Text("Cancel")
                    }
                },
                text = {
                    TodoFormInputs()
                }
            )
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            if (showActionButton) {
                FloatingActionButton(
                    onClick = {
                        dialogOpen.value = true
                    }
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add Todo")
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = innerPadding.calculateTopPadding() + 10.dp)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            content()
        }
    }
}