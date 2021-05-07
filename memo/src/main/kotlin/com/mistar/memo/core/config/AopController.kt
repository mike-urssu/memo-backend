package com.mistar.memo.core.config

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Aspect
@Component
class AopController {
    private val logger = LoggerFactory.getLogger(AopController::class.java)

    @Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping")
    fun getMapping() {
        logger.info("${LocalDateTime.now()}")
        logger.info("GetMapping called")
    }

    @Before("getMapping()")
    fun before(joinPoint: JoinPoint) {
        logger.info("join point")
        logger.info("Before logging start")
    }

}