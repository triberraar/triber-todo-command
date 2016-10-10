package be.tribersoft.command

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