package be.tribersoft.command.commands

import org.axonframework.commandhandling.TargetAggregateIdentifier
import java.util.*

data class CreateTodoItemCommand(@TargetAggregateIdentifier val todoListUuid: UUID, val uuid: UUID, val description: String)
data class UpdateTodoItemDescriptionCommand(@TargetAggregateIdentifier val todoListUuid: UUID, val uuid: UUID, val description: String)
data class FinishTodoItemCommand(@TargetAggregateIdentifier val todoListUuid: UUID, val uuid: UUID)
data class StartTodoItemCommand(@TargetAggregateIdentifier val todoListUuid: UUID, val uuid: UUID)