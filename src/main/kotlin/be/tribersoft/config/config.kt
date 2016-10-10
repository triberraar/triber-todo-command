package be.tribersoft.config

import org.axonframework.eventsourcing.eventstore.EventStorageEngine
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine
import org.axonframework.spring.config.EnableAxon
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class AxonConfig {

    @Bean
    open fun eventStorageEngine(): EventStorageEngine {
        return InMemoryEventStorageEngine()
    }


}