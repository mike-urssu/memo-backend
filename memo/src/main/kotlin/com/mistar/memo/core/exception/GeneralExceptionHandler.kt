package com.mistar.memo.core.exception

import com.mistar.memo.core.response.ErrorResponse
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
class GeneralExceptionHandler {
    @ExceptionHandler(NotYourContentsException::class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    fun handleNotYourContents(exception: NotYourContentsException): ErrorResponse {
        return ErrorResponse(HttpStatus.FORBIDDEN, "System-001", exception.message!!)
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleBadRequestBody(exception: HttpMessageNotReadableException): ErrorResponse {
        return ErrorResponse(HttpStatus.BAD_REQUEST, "System-002", "wrong request body")
    }


    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleRequestValid(exception: MethodArgumentNotValidException): ErrorResponse {
        val builder = StringBuilder()
        for (fieldError in exception.bindingResult.fieldErrors) {
            builder.append("[${fieldError.field}](은)는 ${fieldError.defaultMessage} 입력된 값: [${fieldError.rejectedValue}]")
        }
        return ErrorResponse(HttpStatus.BAD_REQUEST, "System-003", builder.toString())
    }

    @ExceptionHandler(IllegalStateException::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleAmbiguousUrl(exception: IllegalStateException): ErrorResponse {
        return ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "System-004", "ambiguous Url Mapping")
    }
}