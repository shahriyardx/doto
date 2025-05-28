package com.shahriyardx.doto

import com.shahriyardx.doto.database.todo.TodoEntity

sealed class TodoAction {
    object Add: TodoAction()
    data class Delete(val todo: TodoEntity): TodoAction()
    data class Completed(val todo: TodoEntity): TodoAction()
    data class SetTitle(val title: String): TodoAction()
    data class SetDescription(val description: String): TodoAction()
}