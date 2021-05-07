package com.mistar.memo.core.config

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.*
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Aspect
@Component
class AopController {
    private val logger = LoggerFactory.getLogger(AopController::class.java)

    @Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping")
    fun getMapping() {
        logger.info("${LocalDateTime.now()} 111 GetMapping called")
    }

    @Before("getMapping()")
    fun before(joinPoint: JoinPoint) {
        logger.info("${LocalDateTime.now()} 222 before called")
    }

    @AfterReturning(pointcut = "getMapping()", returning = "result")
    fun afterReturning(joinPoint: JoinPoint, result: Object) {
        logger.info("${LocalDateTime.now()} 333 afterReturning called")
    }

    @Around("GetMapping()")
    fun around(joinPoint: ProceedingJoinPoint):Object {

    }
}