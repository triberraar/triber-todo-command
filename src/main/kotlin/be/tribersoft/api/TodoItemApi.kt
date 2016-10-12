package be.tribersoft.api

import org.axonframework.commandhandling.TargetAggregateIdentifier
import java.util.*

data class CreateTodoItemCommand(@TargetAggregateIdentifier val todoListUuid: UUID, val uuid: UUID, val description: String)
data class UpdateTodoItemDescriptionCommand(@TargetAggregateIdentifier val todoListUuid: UUID, val uuid: UUID, val description: String)
data class FinishTodoItemCommand(@TargetAggregateIdentifier val todoListUuid: UUID, val uuid: UUID)
data class StartTodoItemCommand(@TargetAggregateIdentifier val todoListUuid: UUID, val uuid: UUID)

data class TodoItemCreatedEvent(val todoListUuid: UUID, val uuid: UUID, val description: String, val status: TodoItemStatus = TodoItemStatus.STARTED)
data class TodoItemDescriptionUpdatedEvent(val todoListUuid: UUID, val uuid: UUID, val description: String)
data class TodoItemFinishedEvent(val todoListUuid: UUID, val uuid: UUID)
data class TodoItemStartedEvent(val todoListUuid: UUID, val uuid: UUID)

enum class TodoItemStatus {
    STARTED, FINISHED
}

class TodoItemAlreadyFinishedException : RuntimeException()
class TodoItemAlreadyStartedException : RuntimeException()
class TodoItemNotFoundException : RuntimeException()