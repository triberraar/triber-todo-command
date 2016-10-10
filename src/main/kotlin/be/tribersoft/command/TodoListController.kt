package be.tribersoft.command

import be.tribersoft.api.CreateTodoListCommand
import be.tribersoft.api.UpdateTodoListNameCommand
import org.axonframework.commandhandling.CommandBus
import org.axonframework.commandhandling.GenericCommandMessage
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.scheduling.annotation.Async
import org.springframework.web.bind.annotation.*
import java.util.*
import java.util.concurrent.CompletableFuture
import javax.inject.Inject
import javax.ws.rs.core.MediaType

@RestController
@RequestMapping(path = arrayOf("/todo-list"))
class TodoListController
@Inject
constructor(private val commandBus: CommandBus, private val commandGateway: CommandGateway) {

    @PostMapping(consumes = arrayOf(MediaType.APPLICATION_JSON), produces = arrayOf(MediaType.APPLICATION_JSON))
    fun create(@RequestBody body: CreateTodoListJson): CompletableFuture<TodoListJson> {
        val uuid = UUID.randomUUID()
        val future = commandGateway.send<Any>(GenericCommandMessage.asCommandMessage<Any>(CreateTodoListCommand(uuid, body.name)))
        return future.thenApply { TodoListJson(uuid, body.name) }
    }

    @PutMapping(path = arrayOf("/{uuid}"), consumes = arrayOf(MediaType.APPLICATION_JSON), produces = arrayOf(MediaType.APPLICATION_JSON))
    @Async
    fun update(@PathVariable uuid: UUID, @RequestBody body: UpdateTodoListJson): CompletableFuture<TodoListJson> {
        val future = commandGateway.send<Any>(GenericCommandMessage.asCommandMessage<Any>(UpdateTodoListNameCommand(uuid, body.name)))
        return future.thenApply { TodoListJson(uuid, body.name) }

    }
}