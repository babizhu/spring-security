package com.bbz.spring.learning.security.demo.web.async

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class MockQueue {
    private val log = LoggerFactory.getLogger(javaClass)
    var placeOrder: String? = null
        set (value) {
            Thread {
                log.info("接到下单请求:$value")
                Thread.sleep(1000)
                this.completeOrder = value
                log.info("下单请求处理完毕：$value")
            }.start()
        }
    var completeOrder: String? = null

}
//
//fun main(args: Array<String>) {
//    MockQueue().placeOrder = "abcd"
//}