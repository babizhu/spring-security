package com.bbz.spring.learning.security.demo.web.async

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.async.DeferredResult
import java.util.*

@RestController
class AsyncController {
    companion object {
        val log = LoggerFactory.getLogger(AsyncController::class.java)
    }
    @Autowired
    lateinit var mockQueue: MockQueue

    @Autowired
    lateinit var deferredResultHolder: DeferredResultHolder
    @RequestMapping("/order")
    fun order(): DeferredResult<String> {
        log.info("主线程开始")
//        val result = Callable {
//            log.info("副线程开始")
//            Thread.sleep(1000)
//            log.info("副线程结束")
//            return@Callable "success"
//        }
        val orderNumber = Random().nextInt(1000000).toString()
        mockQueue.placeOrder = orderNumber
        val result = DeferredResult<String>()
        deferredResultHolder.map[orderNumber] = result
        log.info("主线程返回")
        return result

    }
}