package be.tribersoft.query.projection

import be.tribersoft.api.TodoListCreatedEvent
import org.axonframework.eventhandling.EventHandler
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import javax.inject.Inject

@Component
class TodoListService @Inject constructor(private val todoListRepository: TodoListRepository) {

    val logger: Logger = LoggerFactory.getLogger(TodoListService::class.java)

    @EventHandler
    fun on(event: TodoListCreatedEvent) {
        logger.info("Projection Creating todolist")
        todoListRepository.save(TodoList(event.uuid.toString(), event.name))
    }
//
//    @EventHandler
//    fun on(event: TodoListNameUpdatedEvent) {
//        val todoList = todoListRepository.findOne(event.uuid.toString())
//        todoList.name = event.name
//        todoListRepository.save(todoList)
//    }
//
//    @EventHandler
//    fun on(event: TodoItemCreatedEvent) {
//        val todoList = todoListRepository.findOne(event.todoListUuid.toString())
//        todoList.todoItems.add(TodoItem(event.uuid.toString(), event.description, event.status))
//        todoListRepository.save(todoList)
//    }
//
//    @EventHandler
//    fun on(event: TodoItemDescriptionUpdatedEvent) {
//        val todoList = todoListRepository.findOne(event.todoListUuid.toString())
//        todoList.todoItems.first { it.uuid == event.uuid.toString() }.description = event.description;
//        todoListRepository.save(todoList)
//    }
//
//    @EventHandler
//    fun on(event: TodoItemStartedEvent) {
//        val todoList = todoListRepository.findOne(event.todoListUuid.toString())
//        todoList.todoItems.first { it.uuid == event.uuid.toString() }.status = TodoItemStatus.STARTED
//        todoListRepository.save(todoList)
//    }
//
//    @EventHandler
//    fun on(event: TodoItemFinishedEvent) {
//        val todoList = todoListRepository.findOne(event.todoListUuid.toString())
//        todoList.todoItems.first { it.uuid == event.uuid.toString() }.status = TodoItemStatus.FINISHED
//        todoListRepository.save(todoList)
//    }

}
