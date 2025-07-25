package com.shahriyardx.doto.viewmodels.todo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shahriyardx.doto.database.todo.TodoEntity
import com.shahriyardx.doto.repositories.TodoRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class TodoViewModel(
    private val repository: TodoRepository,
) : ViewModel() {
    private val _todoFilter = MutableStateFlow<TodoFilter>(TodoFilter.ALL)

    private val _todos = _todoFilter.flatMapLatest {
        repository.getTodos(it)
    }.stateIn(viewModelScope, SharingStarted.Companion.WhileSubscribed(), emptyList())

    private val _state = MutableStateFlow(TodoState())
    val state = combine(_state, _todoFilter, _todos) { state, todoFilter, todos ->
        state.copy(
            todos = todos,
            filterType = todoFilter,
        )
    }.stateIn(viewModelScope, SharingStarted.Companion.WhileSubscribed(), TodoState())

    val dialogOpen = mutableStateOf(false)

    fun openDialog() {
        dialogOpen.value = true
    }

    fun closeDialog() {
        dialogOpen.value = false
    }

    fun onEvent(event: TodoAction, onFinish: () -> Unit = {}) {
        when (event) {
            TodoAction.Add -> addTodo()
            is TodoAction.Delete -> deleteTodo(event.todo)
            is TodoAction.Completed -> toggleCompletion(event.todo)
            is TodoAction.SetTitle -> setTitle(event.title)
            is TodoAction.SetDescription -> setDesc(event.description)
            is TodoAction.Filter -> setFilter(event.filterType)
        }

        onFinish()
    }

    fun setFilter(filter: TodoFilter) {
        _todoFilter.value = filter
        _state.update { it ->
            it.copy(
                filterType = filter
            )
        }
    }

    fun setTitle(title: String) {
        _state.value = _state.value.copy(
            title = title,
        )
    }

    fun setDesc(desc: String) {
        _state.value = _state.value.copy(
            description = desc,
        )
    }

    fun addTodo() {
        viewModelScope.launch {
            val todo = TodoEntity(state.value.title, state.value.description)
            val id = repository.addTodo(todo)
            val todoWithId = todo.copy(id = id.toInt())

            _state.update { currentState ->
                currentState.copy(
                    todos = currentState.todos + todoWithId,
                    title = "",
                    description = "",
                )
            }
        }
    }

    fun toggleCompletion(todo: TodoEntity) {
        viewModelScope.launch {
            repository.updateTodo(todo.copy(isComplete = !todo.isComplete))
        }

        _state.update { it ->
            it.copy(
                todos = it.todos.map { it ->
                    if (todo.id == it.id) it.copy(isComplete = !it.isComplete) else it
                })
        }
    }

    fun deleteTodo(todo: TodoEntity) {
        viewModelScope.launch {
            repository.deleteTodo(todo)
        }

        _state.update { it ->
            it.copy(
                todos = it.todos.filter { it ->
                    it.id != todo.id
                })
        }
    }
}