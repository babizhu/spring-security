package com.bbz.spring.learning.security.core.validate.code.sms

interface ISmsCodeSender {
    fun send(mobile:String,code:String)
}