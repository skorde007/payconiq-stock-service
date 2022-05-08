package com.payconiq.stock.exception

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.util.stream.Collectors
import javax.validation.ConstraintViolation
import javax.validation.ConstraintViolationException


@ControllerAdvice
class RestExceptionHandler: ResponseEntityExceptionHandler() {

    protected override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders, status: HttpStatus, request: WebRequest
    ): ResponseEntity<Any> {
        val errors = ex.bindingResult.fieldErrors.stream().map { obj: FieldError -> obj.defaultMessage }
            .collect(Collectors.toList())
        val body: MutableMap<String, Any> = LinkedHashMap()
        body["message"] = errors
        return ResponseEntity(body, headers, status!!)
    }

    @ExceptionHandler(NullPointerException::class)
    protected fun handleNullPointer(ex: NullPointerException): ResponseEntity<Any?>? {
        val errors: List<String> = listOf("Data Not present" + ": " + ex.localizedMessage)
        val body: MutableMap<String, Any> = LinkedHashMap()
        body["message"] = errors
        return ResponseEntity(body, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(ConstraintViolationException::class)
    protected fun handleConstraintViolationException(ex: ConstraintViolationException): ResponseEntity<Any?>? {
        val body: MutableMap<String, Any> = LinkedHashMap()
        body["message"] = ex.constraintViolations.stream().map { x: ConstraintViolation<*> -> x.message }
        return ResponseEntity(body, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(NoSuchElementException::class)
    protected fun handleNoSuchElementException(ex: NoSuchElementException): ResponseEntity<Any?>? {
        val body: MutableMap<String, Any?> = LinkedHashMap()
        body["message"] = ex.message
        return ResponseEntity(body, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(StockFoundException::class)
    protected fun handleStockFoundException(ex: StockFoundException): ResponseEntity<Any?>? {
        val body: MutableMap<String, Any> = LinkedHashMap()
        body["message"] = ex.localizedMessage
        return ResponseEntity(body, HttpStatus.FOUND)
    }
}