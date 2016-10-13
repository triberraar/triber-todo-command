package be.tribersoft.api

import java.time.LocalDateTime
import java.util.*

data class TodoListCreatedEvent(val uuid: UUID, val name: String, var timestamp: LocalDateTime = LocalDateTime.now())
data class TodoListNameUpdatedEvent(val uuid: UUID, val name: String, var timestamp: LocalDateTime = LocalDateTime.now())
