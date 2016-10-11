package be.tribersoft.command

import be.tribersoft.api.CreateTodoItemCommand
import be.tribersoft.api.FinishTodoItemCommand
import be.tribersoft.api.StartTodoItemCommand
import be.tribersoft.api.UpdateTodoItemDescriptionCommand
import org.axonframework.commandhandling.GenericCommandMessage
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.web.bind.annotation.*
import java.util.*
import java.util.concurrent.CompletableFuture
import javax.inject.Inject
import javax.ws.rs.core.MediaType

@RestController
@RequestMapping(path = arrayOf("/todo-list/{todoListUuid}/todo-item"))
class TodoItemController
@Inject
constructor(private val commandGateway: CommandGateway) {

    @PostMapping(consumes = arrayOf(MediaType.APPLICATION_JSON), produces = arrayOf(MediaType.APPLICATION_JSON))
    fun create(@PathVariable todoListUuid: UUID, @RequestBody body: CreateTodoItemJson): CompletableFuture<TodoItemJson> {
        val uuid = UUID.randomUUID()
        val future = commandGateway.send<Any>(GenericCommandMessage.asCommandMessage<Any>(CreateTodoItemCommand(todoListUuid, uuid, body.description)))
        return future.thenApply { TodoItemJson(uuid) }
    }

    @PutMapping(path = arrayOf("/{uuid}"), consumes = arrayOf(MediaType.APPLICATION_JSON), produces = arrayOf(MediaType.APPLICATION_JSON))
    fun update(@PathVariable todoListUuid: UUID, @PathVariable uuid: UUID, @RequestBody body: UpdateTodoItemJson): CompletableFuture<TodoItemJson> {
        val future = commandGateway.send<Any>(GenericCommandMessage.asCommandMessage<Any>(UpdateTodoItemDescriptionCommand(todoListUuid, uuid, body.description)))
        return future.thenApply { TodoItemJson(uuid) }
    }

    @PutMapping(path = arrayOf("/{uuid}/finish"), consumes = arrayOf(MediaType.APPLICATION_JSON), produces = arrayOf(MediaType.APPLICATION_JSON))
    fun finish(@PathVariable todoListUuid: UUID, @PathVariable uuid: UUID): CompletableFuture<TodoItemJson> {
        val future = commandGateway.send<Any>(GenericCommandMessage.asCommandMessage<Any>(FinishTodoItemCommand(todoListUuid, uuid)))
        return future.thenApply { TodoItemJson(uuid) }
    }

    @PutMapping(path = arrayOf("/{uuid}/start"), consumes = arrayOf(MediaType.APPLICATION_JSON), produces = arrayOf(MediaType.APPLICATION_JSON))
    fun start(@PathVariable todoListUuid: UUID, @PathVariable uuid: UUID): CompletableFuture<TodoItemJson> {
        val future = commandGateway.send<Any>(GenericCommandMessage.asCommandMessage<Any>(StartTodoItemCommand(todoListUuid, uuid)))
        return future.thenApply { TodoItemJson(uuid) }
    }
}