package com.bbz.spring.learning.security.core.validate.code

import org.springframework.web.context.request.ServletWebRequest

interface ValidateCodeGenerator {
    fun generator(request: ServletWebRequest): ImageCode
}