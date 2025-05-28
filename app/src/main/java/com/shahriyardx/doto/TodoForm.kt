package com.shahriyardx.doto

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun TodoForm(
) {
    val viewModel = LocalViewModelComposition.current
    val state = viewModel.state.collectAsState()

    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(text = "Add new todo", fontSize = 25.sp, fontWeight = FontWeight.Bold)

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.value.title,
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
            value = state.value.description,
            placeholder = { Text("Enter Description") },
            onValueChange = {
                viewModel.onEvent(TodoAction.SetDescription(it))
            },
            shape = RoundedCornerShape(8.dp),
        )

        Button(
            shape = RoundedCornerShape(8.dp),
            onClick = {
                viewModel.onEvent(TodoAction.Add)
            }
        ) {
            Text(text = "Add to database")
        }
    }
}