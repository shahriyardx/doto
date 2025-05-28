package com.shahriyardx.doto

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shahriyardx.doto.database.todo.TodoDao
import com.shahriyardx.doto.database.todo.TodoEntity
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
    private val dao: TodoDao
) : ViewModel() {
    private val _todoFilter = MutableStateFlow<TodoFilter>(TodoFilter.ALL)
    private val _todos = _todoFilter.flatMapLatest { filterType ->
        when (filterType) {
            TodoFilter.ALL -> dao.getAllTodos()
            TodoFilter.COMPLETED -> dao.getCompletedTodos()
            TodoFilter.INCOMPLETE -> dao.getIncompleteTodos()
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _state = MutableStateFlow(TodoState())

    val state = combine(_state, _todoFilter, _todos) { state, todoFilter, todos ->
        state.copy(
            todos = todos,
            filterType = todoFilter,
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), TodoState())

    fun onEvent(event: TodoAction) {
        when (event) {
            is TodoAction.Add -> addTodo()
            is TodoAction.Delete -> deleteTodo(event.todo)
            is TodoAction.Completed -> toggleCompletion(event.todo)
            is TodoAction.SetTitle -> setTitle(event.title)
            is TodoAction.SetDescription -> setDesc(event.description)
            is TodoAction.Filter -> setFilter(event.filterType)
        }
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
            val id = dao.insert(todo)
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
            dao.update(todo.copy(isComplete = !todo.isComplete))
        }

        _state.update { it ->
            it.copy(
                todos = it.todos.map { it ->
                    if (todo.id == it.id) it.copy(isComplete = !it.isComplete) else it
                }
            )
        }
    }

    fun deleteTodo(todo: TodoEntity) {
        viewModelScope.launch {
            dao.delete(todo)
        }

        _state.update { it ->
            it.copy(
                todos = it.todos.filter { it ->
                    it.id != todo.id
                }
            )
        }
    }
}