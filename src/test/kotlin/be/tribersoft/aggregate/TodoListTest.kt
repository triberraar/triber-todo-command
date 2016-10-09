package be.tribersoft.aggregate

import be.tribersoft.api.*
import org.axonframework.test.FixtureConfiguration
import org.axonframework.test.Fixtures
import org.junit.Before
import org.junit.Test

class TodoListTest {

    var fixture: FixtureConfiguration<TodoList>? = null

    @Before
    fun setUp() {
        fixture = Fixtures.newGivenWhenThenFixture(TodoList::class.java)
    }

    @Test
    fun todoListCreate() {
        fixture!!.givenNoPriorActivity().
            `when`(CreateTodoListCommand("uuid", "name")).
            expectEvents(TodoListCreatedEvent("uuid", "name"))

    }

    @Test
    fun todoListUpdateName() {
        fixture!!.given(TodoListCreatedEvent("uuid", "name")).
                `when`(UpdateTodoListNameCommand("uuid", "new name")).
                expectEvents(TodoListNameUpdatedEvent("uuid", "new name"))

    }

    @Test
    fun todoItemCreate() {
        fixture!!.given(TodoListCreatedEvent("todo list uuid", "name")).
                `when`(CreateTodoItemCommand("todo list uuid", "uuid", "description")).
                expectEvents(TodoItemCreatedEvent("todo list uuid", "uuid", "description"))
    }

    @Test
    fun todoItemUpdateDescription() {
        fixture!!.given(TodoListCreatedEvent("todo list uuid", "name"), TodoItemCreatedEvent("todo list uuid", "uuid", "description")).
                `when`(UpdateTodoItemDescriptionCommand("todo list uuid", "uuid", "new description")).
                expectEvents(TodoItemDescriptionUpdatedEvent("todo list uuid", "uuid", "new description"))

    }

    @Test
    fun todoItemFinish_startedTodoItem() {
        fixture!!.given(TodoListCreatedEvent("todo list uuid", "name"), TodoItemCreatedEvent("todo list uuid", "uuid", "description")).
                `when`(FinishTodoItemCommand("todo list uuid", "uuid")).
                expectEvents(TodoItemFinishedEvent("todo list uuid", "uuid"))
    }

    @Test
    fun todoItemFinish_finishedTodoItem() {
        fixture!!.given(TodoListCreatedEvent("todo list uuid", "name"), TodoItemCreatedEvent("todo list uuid", "uuid", "description"), TodoItemFinishedEvent("todo list uuid", "uuid")).
                `when`(FinishTodoItemCommand("todo list uuid", "uuid")).
                expectNoEvents().
                expectException(TodoItemAlreadyFinishedException::class.java)
    }

    @Test
    fun todoItemStart_finishedTodoItem() {
        fixture!!.given(TodoListCreatedEvent("todo list uuid", "name"), TodoItemCreatedEvent("todo list uuid", "uuid", "description"), TodoItemFinishedEvent("todo list uuid", "uuid")).
                `when`(StartTodoItemCommand("todo list uuid", "uuid")).
                expectEvents(TodoItemStartedEvent("todo list uuid", "uuid"))
    }

    @Test
    fun todoItemStart_startedTodoItem() {
        fixture!!.given(TodoListCreatedEvent("todo list uuid", "name"), TodoItemCreatedEvent("todo list uuid", "uuid", "description")).
                `when`(StartTodoItemCommand("todo list uuid", "uuid")).
                expectNoEvents().
                expectException(TodoItemAlreadyStartedException::class.java)
    }
}
