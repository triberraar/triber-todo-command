package be.tribersoft.command.rest

import be.tribersoft.api.TodoItemAlreadyFinishedException
import be.tribersoft.api.TodoItemAlreadyStartedException
import be.tribersoft.api.TodoItemNotFoundException
import org.axonframework.commandhandling.model.AggregateNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class AggregateNotFoundExceptionHandler() {
    @ExceptionHandler(AggregateNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    fun handle(e: AggregateNotFoundException): ErrorJson {
        return ErrorJson("Aggregate not found")
    }
}

@ControllerAdvice
class TodoItemNotFoundExceptionHandler() {
    @ExceptionHandler(TodoItemNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    fun handle(e: TodoItemNotFoundException): ErrorJson {
        return ErrorJson("Todo item not found")
    }
}

@ControllerAdvice
class TodoItemAlreadyFinishedExceptionHandler() {
    @ExceptionHandler(TodoItemAlreadyFinishedException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    fun handle(e: TodoItemAlreadyFinishedException): ErrorJson {
        return ErrorJson("Todo item already finished")
    }
}

@ControllerAdvice
class TodoItemAlreadyStartedExceptionHandler() {
    @ExceptionHandler(TodoItemAlreadyStartedException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    fun handle(e: TodoItemAlreadyStartedException): ErrorJson {
        return ErrorJson("Todo item already started")
    }
}