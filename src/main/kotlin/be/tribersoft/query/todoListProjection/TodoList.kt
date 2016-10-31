package be.tribersoft.query.todoListProjection

import be.tribersoft.api.TodoItemStatus
import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document

@Document(indexName = "todo-list", type = "todo-list", shards = 1, replicas = 0, refreshInterval = "-1")
class TodoList(@Id val uuid: String, var name: String, val todoItems: MutableSet<TodoItem> = mutableSetOf())

class TodoItem(val uuid: String, var description: String, var status: TodoItemStatus)


