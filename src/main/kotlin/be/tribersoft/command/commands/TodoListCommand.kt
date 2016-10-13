package be.tribersoft.command.commands

import org.axonframework.commandhandling.TargetAggregateIdentifier
import java.util.*

data class CreateTodoListCommand(val uuid: UUID, val name: String)
data class UpdateTodoListNameCommand(@TargetAggregateIdentifier val uuid: UUID, val name: String)
