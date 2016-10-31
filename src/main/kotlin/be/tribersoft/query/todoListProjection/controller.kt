package be.tribersoft.query.todoListProjection

import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.ws.rs.core.MediaType

@RestController("queryTodoListController")
@RequestMapping(path = arrayOf("/todo-list"))
class controller(private val todoListRepository: TodoListRepository, private val converter: Converter) {
    @GetMapping(produces = arrayOf(MediaType.APPLICATION_JSON))
    fun all(): List<TodoListJson>? {
        return todoListRepository.all().map { it ->
            converter.convert(it)
        }
    }

    @GetMapping(produces = arrayOf(MediaType.APPLICATION_JSON), path = arrayOf("/{uuid}"))
    fun one(@PathVariable("uuid") uuid: String): TodoListJson {
        val todoList = todoListRepository.one(uuid)
        return converter.convert(todoList)
    }
}

@Component
class Converter {
    fun convert(todoList: TodoList): TodoListJson {
        val todoItemJsons: List<TodoItemJson> = todoList.todoItems.map { it -> TodoItemJson(it.uuid, it.description, it.status) }
        return TodoListJson(todoList.uuid, todoList.name, todoItemJsons)
    }
}