package com.bbz.spring.learning.security.demo.web.filter

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import javax.servlet.*

//@Component
class TimeFilter : Filter {
    companion object {
        val log = LoggerFactory.getLogger(TimeFilter::class.java)!!
    }

    override fun destroy() {
     log.info("time filter destroy")

    }

    override fun doFilter(request: ServletRequest?, response: ServletResponse, chain: FilterChain) {
        log.info("time filter start")
        val start = System.currentTimeMillis()
        chain.doFilter(request,response)
        log.info("time filter 耗时: ${System.currentTimeMillis() - start}")

        log.info("time filter finish")
    }

    override fun init(p0: FilterConfig?) {
        log.info("time filter init")
    }
}