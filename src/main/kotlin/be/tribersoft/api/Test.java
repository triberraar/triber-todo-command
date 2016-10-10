package be.tribersoft.api

import org.axonframework.commandhandling.GenericCommandMessage

/**
 * Created by geertolaerts on 09/10/16.
 */
class Test {

    fun test() {
        GenericCommandMessage.asCommandMessage<Any>(CreateTodoListCommand("", ""))
    }
}
