package be.tribersoft.command

import be.tribersoft.api.CreateTodoListCommand
import be.tribersoft.api.UpdateTodoListNameCommand
import org.axonframework.commandhandling.CommandBus
import org.axonframework.commandhandling.GenericCommandMessage
import org.axonframework.commandhandling.model.AggregateNotFoundException
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.inject.Inject
import javax.ws.rs.core.MediaType

@RestController
@RequestMapping(path = arrayOf("/todo-list"))
class TodoListController
@Inject
constructor(private val commandBus: CommandBus) {

    @PostMapping(consumes = arrayOf(MediaType.APPLICATION_JSON), produces = arrayOf(MediaType.APPLICATION_JSON))
    fun create(@RequestBody body: CreateTodoListJson): TodoListJson {
        val uuid = UUID.randomUUID()
        commandBus.dispatch(GenericCommandMessage.asCommandMessage<Any>(CreateTodoListCommand(uuid, body.name)))
        return TodoListJson(uuid, body.name)
    }

    @PutMapping(path = arrayOf("/{uuid}"), consumes = arrayOf(MediaType.APPLICATION_JSON), produces = arrayOf(MediaType.APPLICATION_JSON))
    fun update(@PathVariable uuid: UUID, @RequestBody body: UpdateTodoListJson): TodoListJson {
        try {
            commandBus.dispatch(GenericCommandMessage.asCommandMessage<Any>(UpdateTodoListNameCommand(uuid, body.name)))
        } catch(e: AggregateNotFoundException) {
            println("----------- not found")
        }
        return TodoListJson(uuid, body.name)
    }
}