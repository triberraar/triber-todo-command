package be.tribersoft.query.projection

import org.springframework.data.repository.CrudRepository

interface TodoListRepository : CrudRepository<TodoList, String> {

}