package be.tribersoft.query

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.inject.Inject
import javax.ws.rs.core.MediaType

@RestController("queryTodoListController")
@RequestMapping(path = arrayOf("/todo-list"))
class TodoListController
@Inject
constructor(private val todoListRepository: TodoListRepository) {
    @GetMapping(produces = arrayOf(MediaType.APPLICATION_JSON))
    fun all(): MutableIterable<TodoList>? {
        return todoListRepository.findAll()
    }
}