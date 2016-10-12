package be.tribersoft.query

import org.springframework.data.repository.CrudRepository

interface TodoListRepository : CrudRepository<TodoList, String> {

}