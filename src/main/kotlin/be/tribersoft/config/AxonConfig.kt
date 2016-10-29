package be.tribersoft.config

import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.mongodb.MongoClient
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine
import org.axonframework.mongo.eventsourcing.eventstore.DefaultMongoTemplate
import org.axonframework.mongo.eventsourcing.eventstore.MongoEventStorageEngine
import org.axonframework.mongo.eventsourcing.eventstore.documentperevent.DocumentPerEventStorageStrategy
import org.axonframework.serialization.json.JacksonSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.data.mongodb.core.MongoTemplate
import java.net.UnknownHostException

@Configuration
@Profile("prod")
open class AxonProductionConfig {

    @Value("\${axon.mongodb.host}")
    lateinit var mongodbHost: String

    @Value("\${axon.mongodb.port}")
    lateinit var mongodbPort: String

    @Bean
    @Throws(UnknownHostException::class)
    open fun mongo(): MongoClient = MongoClient(mongodbHost, mongodbPort.toInt())

    @Bean
    @Throws(UnknownHostException::class)
    open fun mongoSpringTemplate(): MongoTemplate = MongoTemplate(mongo(), "tribertodo")

    @Bean
    @Throws(UnknownHostException::class)
    open fun mongoTemplate(): org.axonframework.mongo.eventsourcing.eventstore.MongoTemplate = DefaultMongoTemplate(mongo(), "tribertodo", "domainevents", "snapshotevents")

    @Bean
    @Throws(UnknownHostException::class)
    open fun eventStorageEngine(): MongoEventStorageEngine {
        val jacksonSerializer = JacksonSerializer()
        jacksonSerializer.objectMapper.registerModule(KotlinModule())
        return MongoEventStorageEngine(jacksonSerializer, null, mongoTemplate(), DocumentPerEventStorageStrategy())
    }

}

@Configuration
@Profile("dev")
open class AxonDevelopmentConfig {
    @Bean
    open fun eventStorageEngine(): InMemoryEventStorageEngine {
        return InMemoryEventStorageEngine();
    }
}