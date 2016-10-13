package be.tribersoft.api

import java.time.LocalDateTime
import java.util.*

data class TodoItemCreatedEvent(val todoListUuid: UUID, val uuid: UUID, val description: String, val status: TodoItemStatus = TodoItemStatus.STARTED, var timestamp: LocalDateTime = LocalDateTime.now())
data class TodoItemDescriptionUpdatedEvent(val todoListUuid: UUID, val uuid: UUID, val description: String, var timestamp: LocalDateTime = LocalDateTime.now())
data class TodoItemFinishedEvent(val todoListUuid: UUID, val uuid: UUID, val status: TodoItemStatus = TodoItemStatus.STARTED, var timestamp: LocalDateTime = LocalDateTime.now())
data class TodoItemStartedEvent(val todoListUuid: UUID, val uuid: UUID, val status: TodoItemStatus = TodoItemStatus.FINISHED, var timestamp: LocalDateTime = LocalDateTime.now())

enum class TodoItemStatus {
    STARTED, FINISHED
}

class TodoItemAlreadyFinishedException : RuntimeException()
class TodoItemAlreadyStartedException : RuntimeException()
class TodoItemNotFoundException : RuntimeException()