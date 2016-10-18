package be.tribersoft.config;

import com.mongodb.MongoClient;
import org.axonframework.mongo.eventsourcing.eventstore.DefaultMongoTemplate;
import org.axonframework.mongo.eventsourcing.eventstore.MongoEventStorageEngine;
import org.axonframework.mongo.eventsourcing.eventstore.documentperevent.DocumentPerEventStorageStrategy;
import org.axonframework.serialization.json.JacksonSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.net.UnknownHostException;

@Configuration
public class AxonJavaConfig {

    @Bean
    public MongoClient mongo() throws UnknownHostException {
        return new MongoClient("127.0.0.1", 27017);
    }

    @Bean
    public MongoTemplate mongoSpringTemplate() throws UnknownHostException {
        return new MongoTemplate(mongo(), "tribertodo");
    }

    @Bean
    public org.axonframework.mongo.eventsourcing.eventstore.MongoTemplate mongoTemplate() throws UnknownHostException {
        return new DefaultMongoTemplate(mongo(), "tribertodo", "domainevents", "snapshotevents");
    }

    @Bean
    public MongoEventStorageEngine eventStorageEngine() throws UnknownHostException {
        return new MongoEventStorageEngine(new JacksonSerializer(), null, mongoTemplate(), new DocumentPerEventStorageStrategy());
    }

}
