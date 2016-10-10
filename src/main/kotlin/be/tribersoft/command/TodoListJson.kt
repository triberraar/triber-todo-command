package be.tribersoft.command

import java.util.*

data class ErrorJson(val error: String)

data class TodoListJson(val uuid: UUID, val name: String)

data class CreateTodoListJson(val name: String = "")
data class UpdateTodoListJson(val name: String = "")

