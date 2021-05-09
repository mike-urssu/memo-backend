package com.mistar.memo.core.exception

import com.mistar.memo.core.response.ErrorResponse
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
class GeneralExceptionHandler {
    @ExceptionHandler(HttpMessageNotReadableException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleBadRequestBody(exception: HttpMessageNotReadableException): ErrorResponse {
        return ErrorResponse(HttpStatus.BAD_REQUEST, "System-001", "wrong request body")
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleRequestValid(exception: MethodArgumentNotValidException): ErrorResponse {
        val builder = StringBuilder()
        for (fieldError in exception.bindingResult.fieldErrors) {
            builder.append("[${fieldError.field}](은)는 ${fieldError.defaultMessage} 입력된 값: [${fieldError.rejectedValue}]")
        }
        return ErrorResponse(HttpStatus.BAD_REQUEST, "System-002", builder.toString())
    }

    @ExceptionHandler(IllegalStateException::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleAmbiguousUrl(exception: IllegalStateException): ErrorResponse {
        return ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "System-003", "ambiguous url mapping")
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    fun handleAmbiguousUrl(exception: HttpRequestMethodNotSupportedException): ErrorResponse {
        return ErrorResponse(HttpStatus.METHOD_NOT_ALLOWED, "System-004", "wrong url mapping")
    }
}