package com.shahriyardx.doto.screens.todo.add_todo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shahriyardx.doto.viewmodels.todo.TodoAction
import com.shahriyardx.doto.viewmodels.todo.TodoViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun TodoFormInputs() {
    val viewModel = koinViewModel<TodoViewModel>()
    val state by viewModel.state.collectAsState()

    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.title,
            placeholder = { Text("Enter Title") },
            maxLines = 1,
            onValueChange = {
                viewModel.onEvent(TodoAction.SetTitle(it))
            },
            shape = RoundedCornerShape(8.dp),
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp),
            value = state.description,
            placeholder = { Text("Enter Description") },
            onValueChange = {
                viewModel.onEvent(TodoAction.SetDescription(it))
            },
            shape = RoundedCornerShape(8.dp),
        )
    }
}