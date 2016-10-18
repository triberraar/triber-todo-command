package be.tribersoft.command.aggregate

import be.tribersoft.api.TodoItemStatus
import be.tribersoft.api.TodoListCreatedEvent
import be.tribersoft.api.TodoListNameUpdatedEvent
import be.tribersoft.command.commands.CreateTodoListCommand
import be.tribersoft.command.commands.UpdateTodoListNameCommand
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

    constructor()

    @CommandHandler
    constructor(command: CreateTodoListCommand) {
        logger.info("Incoming command: Creating todolist: " + command.uuid)
        AggregateLifecycle.apply(TodoListCreatedEvent(command.uuid, command.name))
    }

    @EventSourcingHandler
    fun on(event: TodoListCreatedEvent) {
        logger.info("Incoming event: Create todolist: " + event.uuid)
        uuid = event.uuid
    }

    @CommandHandler
    fun updateName(command: UpdateTodoListNameCommand) {
        logger.info("Incoming command: Updating todolist: " + command.uuid)
        AggregateLifecycle.apply(TodoListNameUpdatedEvent(command.uuid, command.name))
    }

    @EventSourcingHandler
    fun on(event: TodoListNameUpdatedEvent) {
        logger.info("Incocming event: Updating todolist: " + event.uuid)
    }

}

data class TodoItem(val uuid: UUID, var status: TodoItemStatus)
