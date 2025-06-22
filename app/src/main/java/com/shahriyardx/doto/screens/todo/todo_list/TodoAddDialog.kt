package com.shahriyardx.doto.screens.todo.todo_list

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import com.shahriyardx.doto.screens.todo.add_todo.TodoFormInputs
import com.shahriyardx.doto.viewmodels.todo.TodoAction
import com.shahriyardx.doto.viewmodels.todo.TodoViewModel

@Composable
fun TodoAddDialog(
    viewModel: TodoViewModel
) {
    val dialogOpen = viewModel.dialogOpen.value

    when {
        dialogOpen -> {
            AlertDialog(
                onDismissRequest = { viewModel.closeDialog() },
                title = {
                    Text("Add New Todo")
                },
                confirmButton = {
                    Button(onClick = {
                        viewModel.onEvent(TodoAction.Add, onFinish = {
                            viewModel.closeDialog()
                        })
                    }) {
                        Text("Add To Database")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { viewModel.closeDialog() }) {
                        Text("Cancel")
                    }
                },
                text = {
                    TodoFormInputs()
                }
            )
        }
    }
}