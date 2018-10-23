package com.bbz.spring.learning.security.core.validate.code.sms

import com.bbz.spring.learning.security.core.properties.SecurityProperties
import com.bbz.spring.learning.security.core.validate.code.IValidateCodeGenerator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.oauth2.common.util.RandomValueStringGenerator
import org.springframework.stereotype.Component
import org.springframework.web.context.request.ServletWebRequest

@Component("smsValidateCodeGenerator")
//Todo:一切ok之后，测试这个名字和注入的变量名的关系
class SmsCodeGenerator : IValidateCodeGenerator {

    @Autowired
    lateinit var securityProperties: SecurityProperties

    override fun generate(request: ServletWebRequest): SmsCode {
        val stringGenerator = RandomValueStringGenerator()
        stringGenerator.setLength(securityProperties.code.sms.length)

        return SmsCode(stringGenerator.generate(), securityProperties.code.sms.expireIn)
    }
}