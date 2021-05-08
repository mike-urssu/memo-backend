package com.mistar.memo.core.config

import org.aspectj.lang.annotation.AfterReturning
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import java.time.LocalDateTime

@Aspect
@Component
class AopConfig {
    private val logger = LoggerFactory.getLogger(AopConfig::class.java)

    @Before("execution(* *..controller.*.*(..))")
    fun recordRequestURI() {
        val request = (RequestContextHolder.getRequestAttributes() as ServletRequestAttributes).request
        logger.info("${LocalDateTime.now()} ${request.requestURI}")
    }

    @AfterReturning("execution(* *..service.MemoService.deleteMemosByCron(..))", returning = "deletedMemoCount")
    fun recordCronScheduling(deletedMemoCount: Int) {
        if (deletedMemoCount == 0)
            logger.info("No memos are deleted")
        else
            logger.info("$deletedMemoCount memos are deleted by cron")
    }
}