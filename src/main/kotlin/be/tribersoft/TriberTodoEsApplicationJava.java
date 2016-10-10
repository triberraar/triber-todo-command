package be.tribersoft;

import be.tribersoft.api.CreateTodoListCommand;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.GenericCommandMessage;
import org.axonframework.eventhandling.GenericEventMessage;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;
import org.axonframework.spring.config.EnableAxon;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@EnableAxon
@SpringBootApplication
public class TriberTodoEsApplicationJava {

    @Bean
    public EventStorageEngine eventStorageEngine() {
        return new InMemoryEventStorageEngine();
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(TriberTodoEsApplicationJava.class, args);
        CommandBus commandBus = context.getBean(CommandBus.class);
        commandBus.dispatch(GenericCommandMessage.asCommandMessage(new CreateTodoListCommand("uuid", "name")));
    }
}
