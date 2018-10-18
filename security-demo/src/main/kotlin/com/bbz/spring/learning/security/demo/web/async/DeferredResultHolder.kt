package com.bbz.spring.learning.security.demo.web.async

import org.springframework.stereotype.Component
import org.springframework.web.context.request.async.DeferredResult

@Component
class DeferredResultHolder{
    val map = HashMap<String,DeferredResult<String>>()
}