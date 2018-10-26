package com.bbz.spring.learning.security.demo.web.async

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.stereotype.Component
import java.lang.RuntimeException

@Component
class QueueListener : ApplicationListener<ContextRefreshedEvent> {


    private val log = LoggerFactory.getLogger(javaClass)
    override fun onApplicationEvent(p0: ContextRefreshedEvent) {
        Thread {
            while (true) {
                val orderNumber = mockQueue.completeOrder
                if (!orderNumber.isNullOrBlank()) {
                    log.info("返回订单处理结果:$orderNumber")
                    val deferredResult = deferredResultHolder.map[orderNumber]
                    if (deferredResult == null) {
                        throw RuntimeException("订单结果没找到")
                    } else {
                        deferredResult.setResult("place order success")
                        mockQueue.completeOrder = null
                    }
                } else {
                    Thread.sleep(1000)
                }
//                log.info("${Thread.currentThread().name}ContextRefreshedEvent")
            }
        }.start()
    }

    @Autowired
    lateinit var mockQueue: MockQueue
    @Autowired
    lateinit var deferredResultHolder: DeferredResultHolder

}
//
//fun main(args: Array<String>) {
//    val s :MockQueue? = null
//    s?.completeOrder = "43"
//    println("end")
//}