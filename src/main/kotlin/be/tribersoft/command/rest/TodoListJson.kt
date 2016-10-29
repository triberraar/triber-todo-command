package be.tribersoft.command.rest

import java.util.*

data class ErrorJson(val error: String)

data class TodoListJson(val uuid: UUID)

data class CreateTodoItemJson(val description: String)
data class UpdateTodoItemJson(val description: String)

