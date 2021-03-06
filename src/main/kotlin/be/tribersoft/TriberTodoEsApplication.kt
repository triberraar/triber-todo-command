package be.tribersoft

import org.axonframework.spring.config.EnableAxon
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient


@EnableAxon
@SpringBootApplication
@EnableDiscoveryClient
open class TriberTodoEsApplicationKotlin {

}

fun main(args: Array<String>) {
    SpringApplication.run(TriberTodoEsApplicationKotlin::class.java, *args)
}

