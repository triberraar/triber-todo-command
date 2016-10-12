package be.tribersoft.query

import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document

@Document(indexName = "todo-list", type = "todo-list", shards = 1, replicas = 0, refreshInterval = "-1")
class TodoList(@Id var uuid: String = "", var name: String = "") {

}
