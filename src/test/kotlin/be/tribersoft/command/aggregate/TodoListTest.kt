package be.tribersoft.command.aggregate

import be.tribersoft.api.*
import be.tribersoft.command.commands.*
import org.axonframework.test.FixtureConfiguration
import org.axonframework.test.Fixtures
import org.junit.Before
import org.junit.Test
import java.util.*

class TodoListTest {

    var fixture: FixtureConfiguration<TodoList>? = null

    @Before
    fun setUp() {
        fixture = Fixtures.newGivenWhenThenFixture(TodoList::class.java)
    }

    @Test
    fun todoListCreate() {
        val uuid = UUID.randomUUID()
        fixture!!.givenNoPriorActivity().
                `when`(CreateTodoListCommand(uuid, "name")).
                expectEvents(TodoListCreatedEvent(uuid, "name"))

    }

    @Test
    fun todoListUpdateName() {
        val uuid = UUID.randomUUID()
        fixture!!.given(TodoListCreatedEvent(uuid, "name")).
                `when`(UpdateTodoListNameCommand(uuid, "new name")).
                expectEvents(TodoListNameUpdatedEvent(uuid, "new name"))

    }

    @Test
    fun todoItemCreate() {
        val todoListUuid = UUID.randomUUID()
        val uuid = UUID.randomUUID()
        fixture!!.given(TodoListCreatedEvent(todoListUuid, "name")).
                `when`(CreateTodoItemCommand(todoListUuid, uuid, "description")).
                expectEvents(TodoItemCreatedEvent(todoListUuid, uuid, "description"))
    }

    @Test
    fun todoItemUpdateDescription() {
        val todoListUuid = UUID.randomUUID()
        val uuid = UUID.randomUUID()
        fixture!!.given(TodoListCreatedEvent(todoListUuid, "name"), TodoItemCreatedEvent(todoListUuid, uuid, "description")).
                `when`(UpdateTodoItemDescriptionCommand(todoListUuid, uuid, "new description")).
                expectEvents(TodoItemDescriptionUpdatedEvent(todoListUuid, uuid, "new description"))

    }

    @Test
    fun todoItemFinish_startedTodoItem() {
        val todoListUuid = UUID.randomUUID()
        val uuid = UUID.randomUUID()
        fixture!!.given(TodoListCreatedEvent(todoListUuid, "name"), TodoItemCreatedEvent(todoListUuid, uuid, "description")).
                `when`(FinishTodoItemCommand(todoListUuid, uuid)).
                expectEvents(TodoItemFinishedEvent(todoListUuid, uuid))
    }

    @Test
    fun todoItemFinish_finishedTodoItem() {
        val todoListUuid = UUID.randomUUID()
        val uuid = UUID.randomUUID()
        fixture!!.given(TodoListCreatedEvent(todoListUuid, "name"), TodoItemCreatedEvent(todoListUuid, uuid, "description"), TodoItemFinishedEvent(todoListUuid, uuid)).
                `when`(FinishTodoItemCommand(todoListUuid, uuid)).
                expectNoEvents().
                expectException(TodoItemAlreadyFinishedException::class.java)
    }

    @Test
    fun todoItemStart_finishedTodoItem() {
        val todoListUuid = UUID.randomUUID()
        val uuid = UUID.randomUUID()
        fixture!!.given(TodoListCreatedEvent(todoListUuid, "name"), TodoItemCreatedEvent(todoListUuid, uuid, "description"), TodoItemFinishedEvent(todoListUuid, uuid)).
                `when`(StartTodoItemCommand(todoListUuid, uuid)).
                expectEvents(TodoItemStartedEvent(todoListUuid, uuid))
    }

    @Test
    fun todoItemStart_startedTodoItem() {
        val todoListUuid = UUID.randomUUID()
        val uuid = UUID.randomUUID()
        fixture!!.given(TodoListCreatedEvent(todoListUuid, "name"), TodoItemCreatedEvent(todoListUuid, uuid, "description")).
                `when`(StartTodoItemCommand(todoListUuid, uuid)).
                expectNoEvents().
                expectException(TodoItemAlreadyStartedException::class.java)
    }
}
