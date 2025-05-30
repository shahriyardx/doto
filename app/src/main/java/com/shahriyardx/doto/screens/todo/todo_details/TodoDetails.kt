package com.shahriyardx.doto.screens.todo.todo_details

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import com.shahriyardx.doto.screens.layouts.AppLayout
import com.shahriyardx.doto.viewmodels.todo_details.TodoDetailsViewModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun DetailsPage(id: Int) {
    val viewModel: TodoDetailsViewModel = koinViewModel<TodoDetailsViewModel>(
        parameters = { parametersOf(id) })
    val todo by viewModel.todo.collectAsState()

    AppLayout() {
        if (todo != null) {
            val nonNullTodo = todo!!
            Column {
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