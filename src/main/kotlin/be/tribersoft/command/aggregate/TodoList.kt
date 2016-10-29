package be.tribersoft.command.aggregate

import be.tribersoft.api.*
import be.tribersoft.command.commands.*
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.commandhandling.model.AggregateIdentifier
import org.axonframework.commandhandling.model.AggregateLifecycle
import org.axonframework.commandhandling.model.AggregateRoot
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.spring.stereotype.Aggregate
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*

@AggregateRoot
@Aggregate
class TodoList {

    val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    @AggregateIdentifier
    var uuid: UUID? = null

    var todoItems: MutableMap<UUID, TodoItem> = mutableMapOf()

    constructor()

    @CommandHandler
    constructor(command: CreateTodoListCommand) {
        logger.info("TodoList (${command.uuid}) Incoming command: Create todoList")
        AggregateLifecycle.apply(TodoListCreatedEvent(command.uuid, command.name))
    }

    @EventSourcingHandler
    fun on(event: TodoListCreatedEvent) {
        logger.info("TodoList (${event.uuid}) Incoming event: TodoList created")
        uuid = event.uuid
    }

    @CommandHandler
    fun updateName(command: UpdateTodoListNameCommand) {
        logger.info("TodoList (${command.uuid}) Incoming command: Update todoList")
        AggregateLifecycle.apply(TodoListNameUpdatedEvent(command.uuid, command.name))
    }

    @EventSourcingHandler
    fun on(event: TodoListNameUpdatedEvent) {
        logger.info("TodoList (${event.uuid}) Incoming event: TodoList updated")
    }

    @CommandHandler
    fun on(command: CreateTodoItemCommand) {
        logger.info("TodoList (${command.todoListUuid}): TodoItem (${command.uuid}) Incoming command: Create todoItem")
        AggregateLifecycle.apply(TodoItemCreatedEvent(command.todoListUuid, command.uuid, command.description))
    }

    @EventSourcingHandler
    fun on(event: TodoItemCreatedEvent) {
        logger.info("TodoList (${event.todoListUuid}): TodoItem (${event.uuid}) Incoming event: TodoItem created")
        todoItems.put(event.uuid, TodoItem(event.uuid, event.status))
    }

    @CommandHandler
    fun on(command: UpdateTodoItemDescriptionCommand) {
        logger.info("TodoList (${command.todoListUuid}): TodoItem (${command.uuid}) Incoming command: Update todoItem")
        getTodoItem(command.uuid)
        AggregateLifecycle.apply(TodoItemDescriptionUpdatedEvent(command.todoListUuid, command.uuid, command.description))
    }

    @EventSourcingHandler
    fun on(event: TodoItemDescriptionUpdatedEvent) {
        logger.info("TodoList (${event.todoListUuid}): TodoItem (${event.uuid}) Incoming event: TodoItem updated")
    }

    @CommandHandler
    fun on(command: FinishTodoItemCommand) {
        logger.info("TodoList (${command.todoListUuid}): TodoItem (${command.uuid}) Incoming command: Finish todoItem")
        if (!getTodoItem(command.uuid).status.equals(TodoItemStatus.STARTED)) {
            throw TodoItemAlreadyFinishedException()
        }
        AggregateLifecycle.apply(TodoItemFinishedEvent(command.todoListUuid, command.uuid))
    }

    @EventSourcingHandler
    fun on(event: TodoItemFinishedEvent) {
        logger.info("TodoList (${event.todoListUuid}): TodoItem (${event.uuid}) Incoming event: TodoItem finished")
        getTodoItem(event.uuid).status = TodoItemStatus.FINISHED
    }

    @CommandHandler
    fun on(command: StartTodoItemCommand) {
        logger.info("TodoList (${command.todoListUuid}): TodoItem (${command.uuid}) Incoming command: Start todoItem")
        if (!getTodoItem(command.uuid).status.equals(TodoItemStatus.FINISHED)) {
            throw TodoItemAlreadyStartedException()
        }
        AggregateLifecycle.apply(TodoItemStartedEvent(command.todoListUuid, command.uuid))
    }

    @EventSourcingHandler
    fun on(event: TodoItemStartedEvent) {
        logger.info("TodoList (${event.todoListUuid}): TodoItem (${event.uuid}) Incoming event: TodoItem started")
        getTodoItem(event.uuid).status = TodoItemStatus.STARTED
    }


    private fun getTodoItem(uuid: UUID): TodoItem {
        return todoItems.getOrElse(uuid) { throw TodoItemNotFoundException(); }
    }

}

data class TodoItem(val uuid: UUID, var status: TodoItemStatus)
