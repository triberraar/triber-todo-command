package be.tribersoft.query

import be.tribersoft.api.*
import org.axonframework.eventhandling.EventHandler
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import javax.inject.Inject

@Component
class TodoListService @Inject constructor(private val todoListRepository: TodoListRepository) {

    val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    @EventHandler
    fun on(event: TodoListCreatedEvent) {
        logger.info("Creatint todolist")
        todoListRepository.save(TodoList(event.uuid.toString(), event.name))
    }

    @EventHandler
    fun on(event: TodoListNameUpdatedEvent) {
    }

    @EventHandler
    fun on(event: TodoItemCreatedEvent) {
    }

    @EventHandler
    fun on(event: TodoItemDescriptionUpdatedEvent) {
    }

    @EventHandler
    fun on(event: TodoItemStartedEvent) {
    }

    @EventHandler
    fun on(event: TodoItemFinishedEvent) {
    }
}
