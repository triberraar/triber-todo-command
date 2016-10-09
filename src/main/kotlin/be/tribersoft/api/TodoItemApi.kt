package be.tribersoft.api

import org.axonframework.commandhandling.TargetAggregateIdentifier

data class CreateTodoItemCommand(@TargetAggregateIdentifier val todoListUuid: String, val uuid:String, val description: String)
data class UpdateTodoItemDescriptionCommand(@TargetAggregateIdentifier val todoListUuid: String, val uuid:String, val description: String)
data class FinishTodoItemCommand(@TargetAggregateIdentifier val todoListUuid: String, val uuid: String)
data class StartTodoItemCommand(@TargetAggregateIdentifier val todoListUuid: String, val uuid: String)

data class TodoItemCreatedEvent(val todoListUuid: String, val uuid:String, val description: String)
data class TodoItemDescriptionUpdatedEvent(val todoListUuid: String, val uuid:String, val description: String)
data class TodoItemFinishedEvent(val todoListUuid: String, val uuid:String)
data class TodoItemStartedEvent(val todoListUuid: String, val uuid:String)

enum class TodoItemState {
    STARTED, FINISHED
}

class TodoItemAlreadyFinishedException : RuntimeException()
class TodoItemAlreadyStartedException : RuntimeException()