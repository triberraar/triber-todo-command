package be.tribersoft.api

import be.tribersoft.util.DateFixator
import java.time.LocalDateTime
import java.util.*

data class TodoListCreatedEvent(val uuid: UUID, val name: String, var timestamp: LocalDateTime = DateFixator.getNow())
data class TodoListNameUpdatedEvent(val uuid: UUID, val name: String, var timestamp: LocalDateTime = DateFixator.getNow())

class TodoListNotFoundException : RuntimeException()