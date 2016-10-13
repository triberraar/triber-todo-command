package be.tribersoft.command.rest

import be.tribersoft.command.commands.CreateTodoListCommand
import be.tribersoft.command.commands.UpdateTodoListNameCommand
import org.axonframework.commandhandling.GenericCommandMessage
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.web.bind.annotation.*
import java.util.*
import java.util.concurrent.CompletableFuture
import javax.ws.rs.core.MediaType

@RestController("commandTodoListController")
@RequestMapping(path = arrayOf("/todo-list"))
class TodoListController(private val commandGateway: CommandGateway) {

    @PostMapping(consumes = arrayOf(MediaType.APPLICATION_JSON), produces = arrayOf(MediaType.APPLICATION_JSON))
    fun create(@RequestBody body: CreateTodoListJson): CompletableFuture<TodoListJson> {
        val uuid = UUID.randomUUID()
        val future = commandGateway.send<Any>(GenericCommandMessage.asCommandMessage<Any>(CreateTodoListCommand(uuid, body.name)))
        return future.thenApply { TodoListJson(uuid) }
    }

    @PutMapping(path = arrayOf("/{uuid}"), consumes = arrayOf(MediaType.APPLICATION_JSON), produces = arrayOf(MediaType.APPLICATION_JSON))
    fun update(@PathVariable uuid: UUID, @RequestBody body: UpdateTodoListJson): CompletableFuture<TodoListJson> {
        val future = commandGateway.send<Any>(GenericCommandMessage.asCommandMessage<Any>(UpdateTodoListNameCommand(uuid, body.name)))
        return future.thenApply { TodoListJson(uuid) }

    }
}