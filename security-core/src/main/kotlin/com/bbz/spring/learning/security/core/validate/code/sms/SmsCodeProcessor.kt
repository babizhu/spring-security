package com.bbz.spring.learning.security.core.validate.code.sms

import com.bbz.spring.learning.security.core.properties.SecurityConstants
import com.bbz.spring.learning.security.core.validate.code.impl.AbstractValidateCodeProcessor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.bind.ServletRequestUtils
import org.springframework.web.context.request.ServletWebRequest
@Component("smsValidateCodeProcessor")
class SmsCodeProcessor : AbstractValidateCodeProcessor<SmsCode>() {
    @Autowired
    private lateinit var smsCodeSender: SmsCodeSender

    override fun send(request: ServletWebRequest, validateCode: SmsCode) {
        val paramName = SecurityConstants.DEFAULT_PARAMETER_NAME_MOBILE
        val mobile = ServletRequestUtils.getRequiredStringParameter(request.request, paramName)
        smsCodeSender.send(mobile, validateCode.code)
    }
}