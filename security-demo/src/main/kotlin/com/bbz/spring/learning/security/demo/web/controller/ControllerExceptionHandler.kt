package com.bbz.spring.learning.security.demo.web.controller

import com.bbz.spring.learning.security.demo.exception.UserNotExistException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class ControllerExceptionHandler {

    @ExceptionHandler(UserNotExistException::class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handlerUserNotExistException(ex: UserNotExistException): Map<String, String?> {
        return mapOf("id" to ex.id, "message" to ex.message)
    }
}