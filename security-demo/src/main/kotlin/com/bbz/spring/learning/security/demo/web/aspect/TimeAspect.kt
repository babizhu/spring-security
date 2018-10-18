package com.bbz.spring.learning.security.demo.web.aspect

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Aspect
@Component
class TimeAspect {
    companion object {
        val log = LoggerFactory.getLogger(TimeAspect::class.java)!!
    }

    @Around("execution(* com.bbz.spring.learning.security.demo.web.controller.UserController.*(..))")
    fun handlerControllerMethod(pjp: ProceedingJoinPoint): Any {
        log.info("time aspect start")
        pjp.args.forEach { println("arg is $it") }
        val start = System.currentTimeMillis()
        val result = pjp.proceed()
        log.info("time aspect 耗时: ${System.currentTimeMillis() - start}")

        log.info("time aspect end")
        return result
    }
}