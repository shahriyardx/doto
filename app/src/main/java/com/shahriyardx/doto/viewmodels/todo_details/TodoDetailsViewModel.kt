package com.shahriyardx.doto.viewmodels.todo_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shahriyardx.doto.repositories.TodoRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class TodoDetailsViewModel(
    private val todoId: Int,
    private val repository: TodoRepository,
): ViewModel() {
    val todo = repository.getTodoById(todoId).stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(), null
    )
}