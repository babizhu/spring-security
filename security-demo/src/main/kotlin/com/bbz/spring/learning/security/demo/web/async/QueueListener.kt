package com.bbz.spring.learning.security.demo.web.async

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.stereotype.Component

@Component
class QueueListener : ApplicationListener<ContextRefreshedEvent> {


    private val log = LoggerFactory.getLogger(javaClass)!!
    override fun onApplicationEvent(p0: ContextRefreshedEvent) {
        Thread {
            while (true) {
                val orderNumber = mockQueue.completeOrder
                if (!orderNumber.isNullOrBlank()) {
                    log.info("返回订单处理结果:$orderNumber")
                    deferredResultHolder.map[orderNumber]!!.setResult("place order success")
                    mockQueue.completeOrder = null
                } else {
                    Thread.sleep(100)
                }
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