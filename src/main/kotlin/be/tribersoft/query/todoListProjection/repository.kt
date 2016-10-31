package be.tribersoft.query.todoListProjection

import be.tribersoft.api.TodoListNotFoundException
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

interface TodoListJpaRepository : CrudRepository<TodoList, String> {

}

@Repository("todoListQueryRepository")
open class TodoListRepository(val jpaRepository: TodoListJpaRepository) {

    open fun all() = jpaRepository.findAll()
    open fun one(uuid: String) = jpaRepository.findOne(uuid) ?: throw TodoListNotFoundException()
    open fun save(todoList: TodoList) = jpaRepository.save(todoList)
}