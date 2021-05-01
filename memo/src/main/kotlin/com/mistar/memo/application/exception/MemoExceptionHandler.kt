package com.mistar.memo.application.exception

import com.mistar.memo.core.response.ErrorResponse
import com.mistar.memo.domain.exception.InvalidPageException
import com.mistar.memo.domain.exception.MemoNotFoundException
import com.mistar.memo.domain.exception.PageOutOfBoundsException
import com.mistar.memo.domain.exception.UserAlreadyExistsException
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
class MemoExceptionHandler {
    @ExceptionHandler(InvalidPageException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleInvalidPage(exception: InvalidPageException): ErrorResponse {
        return ErrorResponse(HttpStatus.BAD_REQUEST, "Page-001", exception.message!!)
    }

    @ExceptionHandler(PageOutOfBoundsException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handlePageOutOfBounds(exception: PageOutOfBoundsException): ErrorResponse {
        return ErrorResponse(HttpStatus.BAD_REQUEST, "Page-002", exception.message!!)
    }

    @ExceptionHandler(MemoNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleMemoNotFound(exception: MemoNotFoundException): ErrorResponse {
        return ErrorResponse(HttpStatus.NOT_FOUND, "Memo-001", exception.message!!)
    }

    @ExceptionHandler(UserAlreadyExistsException::class)
    @ResponseStatus(HttpStatus.CONFLICT)
    fun handleUserAlreadyExists(exception: UserAlreadyExistsException): ErrorResponse {
        return ErrorResponse(HttpStatus.CONFLICT, "Auth-001", exception.message!!)
    }
}