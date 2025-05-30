package com.shahriyardx.doto.screens.todo.todo_details

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.shahriyardx.doto.LocalNavController
import com.shahriyardx.doto.TodosScreen
import com.shahriyardx.doto.screens.layouts.AppLayout
import com.shahriyardx.doto.viewmodels.todo_details.TodoDetailsViewModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun DetailsPage(id: Int) {
    val viewModel: TodoDetailsViewModel = koinViewModel<TodoDetailsViewModel>(
        parameters = { parametersOf(id) })
    val todo by viewModel.todo.collectAsState()
    val navController = LocalNavController.current

    AppLayout() {
        if (todo != null) {
            val nonNullTodo = todo!!
            Column {
                Row(
                    modifier = Modifier
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        modifier = Modifier.clickable(onClick = {
                            navController.navigate(TodosScreen)
                        }),
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = "Back Button",
                    )


                    Text(text = "All Todos")
                }
                HorizontalDivider(
                    color = Color.LightGray,
                    thickness = 1.dp
                )
                Column(modifier = Modifier.padding(top = 8.dp)) {
                    Text(text = getAnnotatedString("Title", nonNullTodo.title))
                    Text(text = getAnnotatedString("Description", nonNullTodo.description))
                    Text(
                        text = getAnnotatedString(
                            "Completed", if (nonNullTodo.isComplete) "Yes" else "No"
                        )
                    )
                }
            }
        }
    }
}

fun getAnnotatedString(
    name: String,
    content: String,
): AnnotatedString {
    return buildAnnotatedString {
        withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
            append("$name: ")
        }
        append(content)
    }
}