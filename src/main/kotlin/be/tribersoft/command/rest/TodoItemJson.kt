package be.tribersoft.command.rest

import java.util.*

data class TodoItemJson(val uuid: UUID)

data class CreateTodoListJson(val name: String = "")
data class UpdateTodoListJson(val name: String = "")