package be.tribersoft.api

import org.axonframework.commandhandling.TargetAggregateIdentifier
import java.util.*

data class CreateTodoListCommand(val uuid: UUID, val name: String)
data class UpdateTodoListNameCommand(@TargetAggregateIdentifier val uuid: UUID, val name: String)

data class TodoListCreatedEvent(val uuid: UUID, val name: String)
data class TodoListNameUpdatedEvent(val uuid: UUID, val name: String)
