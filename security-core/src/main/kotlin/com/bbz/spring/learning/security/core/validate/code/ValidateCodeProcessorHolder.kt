package com.bbz.spring.learning.security.core.validate.code

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ValidateCodeProcessorHolder {

    @Autowired
    private lateinit var validateCodeProcessors: Map<String, IValidateCodeProcessor>
//
    fun findValidateCodeProcessor(type: ValidateCodeType): IValidateCodeProcessor {
        return findValidateCodeProcessor(type.toString().toLowerCase())
    }

    fun findValidateCodeProcessor(type: String): IValidateCodeProcessor {
        val name = type.toLowerCase() + IValidateCodeProcessor::class.java.simpleName.substring(1)//去掉前面的I
        return validateCodeProcessors[name] ?: throw ValidateCodeException("验证码处理器" + name + "不存在")
    }

}
