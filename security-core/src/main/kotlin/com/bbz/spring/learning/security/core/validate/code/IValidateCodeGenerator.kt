package com.bbz.spring.learning.security.core.validate.code

import org.springframework.web.context.request.ServletWebRequest

interface IValidateCodeGenerator {
    fun generate(request: ServletWebRequest): ValidateCode
}