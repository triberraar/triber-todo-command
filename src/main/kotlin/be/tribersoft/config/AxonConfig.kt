package be.tribersoft.config

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.mongodb.MongoClient
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine
import org.axonframework.mongo.eventsourcing.eventstore.DefaultMongoTemplate
import org.axonframework.mongo.eventsourcing.eventstore.MongoEventStorageEngine
import org.axonframework.mongo.eventsourcing.eventstore.documentperevent.DocumentPerEventStorageStrategy
import org.axonframework.serialization.json.JacksonSerializer
import org.elasticsearch.client.Client
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate
import org.springframework.data.elasticsearch.core.EntityMapper
import org.springframework.data.elasticsearch.core.geo.CustomGeoModule
import org.springframework.data.mongodb.core.MongoTemplate
import java.io.IOException
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

@Configuration
open class ElasticsearchConfig {

    @Bean
    open fun mapper(objectMapper: ObjectMapper) = CustomEntityMapper()


    @Bean
    open fun elasticsearchTemplate(client: Client, mapper: EntityMapper) = ElasticsearchTemplate(client, mapper)

}

class CustomEntityMapper : EntityMapper {

    private val objectMapper: ObjectMapper

    init {
        objectMapper = ObjectMapper()
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true)
        objectMapper.registerModule(CustomGeoModule())
        objectMapper.registerModule(KotlinModule())
    }

    @Throws(IOException::class)
    override fun mapToString(`object`: Any) = objectMapper.writeValueAsString(`object`)


    @Throws(IOException::class)
    override fun <T> mapToObject(source: String, clazz: Class<T>) = objectMapper.readValue(source, clazz)

}