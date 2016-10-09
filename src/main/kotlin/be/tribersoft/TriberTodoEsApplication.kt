package be.tribersoft

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
open class TriberTodoEsApplication

fun main(args: Array<String>) {
    SpringApplication.run(TriberTodoEsApplication::class.java, *args)
}
