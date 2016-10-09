package be.tribersoft.api

import org.axonframework.commandhandling.TargetAggregateIdentifier

data class CreateTodoListCommand(val uuid: String, val name: String)
data class UpdateTodoListNameCommand(@TargetAggregateIdentifier val uuid: String, val name: String)

data class TodoListCreatedEvent(val uuid: String, val name: String)
data class TodoListNameUpdatedEvent(val uuid: String, val name: String)
