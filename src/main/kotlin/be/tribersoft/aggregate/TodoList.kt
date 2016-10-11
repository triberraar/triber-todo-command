package be.tribersoft.aggregate

import be.tribersoft.api.*
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.commandhandling.model.AggregateIdentifier
import org.axonframework.commandhandling.model.AggregateLifecycle
import org.axonframework.commandhandling.model.AggregateRoot
import org.axonframework.eventhandling.EventHandler
import org.axonframework.spring.stereotype.Aggregate
import java.util.*

@Aggregate
@AggregateRoot
class TodoList {

    @AggregateIdentifier
    var uuid: UUID? = null

    var todoItems: MutableMap<UUID, TodoItem> = HashMap<UUID, TodoItem>()

    constructor()

    @CommandHandler
    constructor(command: CreateTodoListCommand ) {
        AggregateLifecycle.apply(TodoListCreatedEvent(command.uuid, command.name))
    }

    @EventHandler
    fun on(event: TodoListCreatedEvent) {
        uuid = event.uuid
    }

    @CommandHandler
    fun updateName(command: UpdateTodoListNameCommand) {
        AggregateLifecycle.apply(TodoListNameUpdatedEvent(command.uuid, command.name))
    }

    @CommandHandler
    fun createTodoItem(command: CreateTodoItemCommand) {
        AggregateLifecycle.apply(TodoItemCreatedEvent(command.todoListUuid, command.uuid, command.description))
    }

    @EventHandler
    fun on(event: TodoItemCreatedEvent) {
        todoItems.put(event.uuid, TodoItem(event.uuid, TodoItemStatus.STARTED))
    }

    @CommandHandler
    fun updateTodoItemDescription(command: UpdateTodoItemDescriptionCommand) {
        checkTodoItemExist(command.uuid)
        AggregateLifecycle.apply(TodoItemDescriptionUpdatedEvent(command.todoListUuid, command.uuid, command.description))
    }

    @CommandHandler
    fun finishTodoItem(command: FinishTodoItemCommand) {
        val todoItem = getTodoItem(command.uuid)
        if (todoItem.status.equals(TodoItemStatus.STARTED)) {
            AggregateLifecycle.apply(TodoItemFinishedEvent(command.todoListUuid, command.uuid))
        } else {
            throw TodoItemAlreadyFinishedException()
        }
    }

    @EventHandler
    fun on(event: TodoItemFinishedEvent) {
        val todoItem = getTodoItem(event.uuid)
        todoItem.status = TodoItemStatus.FINISHED
    }

    @CommandHandler
    fun startTodoItem(command: StartTodoItemCommand) {
        val todoItem = getTodoItem(command.uuid)
        if (todoItem.status.equals(TodoItemStatus.FINISHED)) {
            AggregateLifecycle.apply(TodoItemStartedEvent(command.todoListUuid, command.uuid))
        } else {
            throw TodoItemAlreadyStartedException()
        }

    }

    @EventHandler
    fun on(event: TodoItemStartedEvent) {
        val todoItem = getTodoItem(event.uuid)
        todoItem.status = TodoItemStatus.STARTED
    }

    private fun getTodoItem(uuid: UUID): TodoItem {
        checkTodoItemExist(uuid)
        return todoItems.get(uuid)!!
    }

    private fun checkTodoItemExist(uuid: UUID) {
        val todoItem = todoItems.get(uuid)
        todoItem ?: throw TodoItemNotFoundException()
    }

}

data class TodoItem(val uuid: UUID, var status: TodoItemStatus)
