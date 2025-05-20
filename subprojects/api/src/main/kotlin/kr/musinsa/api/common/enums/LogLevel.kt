package kr.musinsa.api.common.enums

import org.slf4j.Logger

enum class LogLevel {
    NONE,
    DEBUG {
        override fun log(logger: Logger, message: String?) {
            if (message != null) {
                logger.debug(message)
            }
        }
    },
    INFO {
        override fun log(logger: Logger, message: String?) {
            if (message != null) {
                logger.info(message)
            }
        }
    },
    ERROR {
        override fun log(logger: Logger, message: String?) {
            if (message != null) {
                logger.error(message)
            }
        }
    };

    open fun log(logger: Logger, message: String?) {}
}
