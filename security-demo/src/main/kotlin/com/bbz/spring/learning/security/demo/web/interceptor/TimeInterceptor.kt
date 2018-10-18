package com.bbz.spring.learning.security.demo.web.interceptor

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class TimeInterceptor:HandlerInterceptor  {
    companion object {
        val log = LoggerFactory.getLogger(TimeInterceptor::class.java)!!
    }
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        log.info("preHandle")
        log.info(handler.toString())
        request.setAttribute("start",System.currentTimeMillis())
        return true
    }

    override fun postHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any, modelAndView: ModelAndView?) {
        log.info("postHandle")
        log.info("time interceptor 耗时：${System.currentTimeMillis() - request.getAttribute("start") as Long}")
    }

    override fun afterCompletion(request: HttpServletRequest, response: HttpServletResponse, handler: Any, ex: Exception?) {
        log.info("afterCompletion")
        log.info("time interceptor 耗时：${System.currentTimeMillis() - request.getAttribute("start") as Long}")
        log.info("exception is $ex")

    }
}