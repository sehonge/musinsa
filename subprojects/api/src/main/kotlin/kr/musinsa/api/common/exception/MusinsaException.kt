package kr.musinsa.api.common.exception

import kr.musinsa.api.common.enums.LogLevel
import org.springframework.http.HttpStatus

class MusinsaException(
    open val level: LogLevel = LogLevel.INFO,
    open val clientMessage: String? = null,
    open val debugMessage: String? = null,
    open val statusCode: HttpStatus = HttpStatus.BAD_REQUEST,
): RuntimeException(debugMessage)
