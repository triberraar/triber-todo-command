package be.tribersoft.query.todoListProjection

import be.tribersoft.api.*
import org.axonframework.eventhandling.EventHandler
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import javax.inject.Inject

@Component
class service @Inject constructor(private val todoListRepository: TodoListRepository) {

    val logger: Logger = LoggerFactory.getLogger(service::class.java)

    @EventHandler
    fun on(event: TodoListCreatedEvent) {
        logger.info("service: TodoList ${event.uuid}: Incoming event todoList created.")
        todoListRepository.save(TodoList(event.uuid.toString(), event.name))
    }

    @EventHandler
    fun on(event: TodoListNameUpdatedEvent) {
        logger.info("TTodoListProjectionService: TodoList ${event.uuid}: Incoming event todoList updated.")
        val todoList = todoListRepository.one(event.uuid.toString())
        todoList.name = event.name
        todoListRepository.save(todoList)
    }

    @EventHandler
    fun on(event: TodoItemCreatedEvent) {
        logger.info("service: TodoList ${event.todoListUuid}: TodoItem: ${event.uuid} Incoming event todoItem created.")
        val todoList = todoListRepository.one(event.todoListUuid.toString())
        todoList.todoItems.add(TodoItem(event.uuid.toString(), event.description, event.status))
        todoListRepository.save(todoList)
    }

    @EventHandler
    fun on(event: TodoItemDescriptionUpdatedEvent) {
        logger.info("service: TodoList ${event.todoListUuid}: TodoItem: ${event.uuid} Incoming event todoItem updated.")
        val todoList = todoListRepository.one(event.todoListUuid.toString())
        todoList.todoItems.first { it.uuid == event.uuid.toString() }.description = event.description;
        todoListRepository.save(todoList)
    }

    @EventHandler
    fun on(event: TodoItemStartedEvent) {
        logger.info("service: TodoList ${event.todoListUuid}: TodoItem: ${event.uuid} Incoming event todoItem started.")
        val todoList = todoListRepository.one(event.todoListUuid.toString())
        todoList.todoItems.first { it.uuid == event.uuid.toString() }.status = TodoItemStatus.STARTED
        todoListRepository.save(todoList)
    }

    @EventHandler
    fun on(event: TodoItemFinishedEvent) {
        logger.info("service: TodoList ${event.todoListUuid}: TodoItem: ${event.uuid} Incoming event todoItem finished.")
        val todoList = todoListRepository.one(event.todoListUuid.toString())
        todoList.todoItems.first { it.uuid == event.uuid.toString() }.status = TodoItemStatus.FINISHED
        todoListRepository.save(todoList)
    }

}
