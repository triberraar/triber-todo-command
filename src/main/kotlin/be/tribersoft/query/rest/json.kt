package be.tribersoft.query.rest

import be.tribersoft.api.TodoItemStatus

data class TodoItemJson(val uuid: String, val description: String, val status: TodoItemStatus)
data class TodoListJson(val uuid: String, val name: String, val todoItems: List<TodoItemJson>)