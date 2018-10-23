package com.bbz.spring.learning.security.core.validate.code.sms

interface SmsCodeSender {
    fun send(mobile:String,code:String)
}