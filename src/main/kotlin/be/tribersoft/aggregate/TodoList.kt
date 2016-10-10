package be.tribersoft.aggregate

import be.tribersoft.api.*
import lombok.NoArgsConstructor
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.commandhandling.model.AggregateIdentifier
import org.axonframework.commandhandling.model.AggregateLifecycle
import org.axonframework.commandhandling.model.AggregateMember
import org.axonframework.commandhandling.model.AggregateRoot
import org.axonframework.eventhandling.EventHandler
import org.axonframework.spring.stereotype.Aggregate
import java.util.*

@Aggregate
@AggregateRoot
class TodoList {

    @AggregateIdentifier
    var uuid:String? = null

    var todoItems:MutableMap<String, TodoItem> = HashMap<String, TodoItem>()

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
       todoItems.put(event.uuid, TodoItem(event.uuid, TodoItemState.STARTED))
    }

    @CommandHandler
    fun updateTodoItemDescription(command: UpdateTodoItemDescriptionCommand) {
        AggregateLifecycle.apply(TodoItemDescriptionUpdatedEvent(command.todoListUuid, command.uuid, command.description))
    }

    @CommandHandler
    fun finishTodoItem(command: FinishTodoItemCommand) {
        val todoItem = todoItems.get(command.uuid)
        if(todoItem!!.state.equals(TodoItemState.STARTED)) {
            AggregateLifecycle.apply(TodoItemFinishedEvent(command.todoListUuid, command.uuid))
        } else {
            throw TodoItemAlreadyFinishedException()
        }
    }

    @EventHandler
    fun on(event: TodoItemFinishedEvent) {
        val todoItem = todoItems.get(event.uuid)
        todoItem!!.state = TodoItemState.FINISHED
    }

    @CommandHandler
    fun startTodoItem(command: StartTodoItemCommand) {
        val todoItem = todoItems.get(command.uuid)
        if(todoItem!!.state.equals(TodoItemState.FINISHED)) {
            AggregateLifecycle.apply(TodoItemStartedEvent(command.todoListUuid, command.uuid))
        } else {
            throw TodoItemAlreadyStartedException()
        }

    }

}

data class TodoItem( val uuid: String,  var state: TodoItemState)
