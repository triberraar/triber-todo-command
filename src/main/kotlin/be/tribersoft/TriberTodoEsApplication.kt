package be.tribersoft

import be.tribersoft.api.CreateTodoItemCommand
import be.tribersoft.api.CreateTodoListCommand
import be.tribersoft.api.TodoListCreatedEvent
import org.axonframework.commandhandling.CommandBus
import org.axonframework.commandhandling.GenericCommandMessage
import org.axonframework.eventsourcing.eventstore.EventStorageEngine
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine
import org.axonframework.spring.config.EnableAxon
import org.hibernate.event.spi.EventSource
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean


@EnableAxon
@SpringBootApplication
open class TriberTodoEsApplicationKotlin {

    @Bean
    open fun eventStorageEngine(): EventStorageEngine {
        return InMemoryEventStorageEngine()
    }

}

fun main(args: Array<String>) {
    val context = SpringApplication.run(TriberTodoEsApplicationKotlin::class.java, *args)
    val commandBus = context.getBean(CommandBus::class.java)
    commandBus.dispatch(GenericCommandMessage.asCommandMessage<Any>(CreateTodoListCommand("uuid", "name")))
}

