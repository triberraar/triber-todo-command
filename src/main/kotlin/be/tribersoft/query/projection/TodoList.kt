package be.tribersoft.query.projection

import be.tribersoft.api.TodoItemStatus
import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document

@Document(indexName = "todo-list", type = "todo-list", shards = 1, replicas = 0, refreshInterval = "-1")
class TodoList(@Id var uuid: String = "", var name: String = "", var todoItems: MutableSet<TodoItem> = mutableSetOf())

class TodoItem(var uuid: String = "", var description: String = "", var status: TodoItemStatus = TodoItemStatus.STARTED)


