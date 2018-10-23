package com.bbz.spring.learning.security.core.validate.code.sms

class DefaultSmsCodeSender : SmsCodeSender {
    override fun send(mobile: String, code: String) {
        println("向手机$mobile 发送短信验证码 $code")
    }
}