package kr.musinsa.api.common.exception

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice(annotations = [RestController::class])
class GlobalExceptionHandler {
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    @ExceptionHandler(MusinsaException::class)
    fun handleMusinsaException(ex: MusinsaException): ResponseEntity<ErrorResponse> {
        ex.level.log(logger, "[musinsaException] ${ex.debugMessage}")
        return ResponseEntity
            .status(ex.statusCode)
            .body(ErrorResponse(ex.clientMessage))
    }

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun exceptionHandler(exception: Exception): ResponseEntity<ErrorResponse> {
        logger.error("[UnExpected Exception]", exception)
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ErrorResponse("요청을 처리하는 중 문제가 발생했습니다. 잠시 후 다시 시도해주세요."))
    }
}
