package com.shahriyardx.doto

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shahriyardx.doto.database.todo.TodoDao
import com.shahriyardx.doto.database.todo.TodoEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class TodoViewModel(
    private val dao: TodoDao
) : ViewModel() {
    private val _state = MutableStateFlow(TodoState())
    val state = _state.asStateFlow()

    init {
        loadTodos()
    }

    fun onEvent(event: TodoAction) {
        when (event) {
            is TodoAction.Add -> addTodo()
            is TodoAction.Delete -> deleteTodo(event.todo)
            is TodoAction.Completed -> toggleCompletion(event.todo)
            is TodoAction.SetTitle -> setTitle(event.title)
            is TodoAction.SetDescription -> setDesc(event.description)
        }
    }

    private fun loadTodos() {
        viewModelScope.launch {
            val todosFromDb = dao.getAllTodos()
            _state.update { it.copy(todos = todosFromDb) }
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

    fun addTodo(): Unit {
        viewModelScope.launch {
            val todo = TodoEntity(state.value.title, state.value.description)
            val id = dao.insert(todo)
            val todoWithId = todo.copy(id=id.toInt())

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

    fun deleteTodo(todo: TodoEntity): Unit {
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