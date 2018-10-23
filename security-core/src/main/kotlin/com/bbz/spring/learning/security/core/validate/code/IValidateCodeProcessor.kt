package com.bbz.spring.learning.security.core.validate.code

import org.springframework.web.context.request.ServletWebRequest

interface IValidateCodeProcessor {

    /**
     * 创建校验码
     *
     * @param request
     * @throws Exception
     */
    @Throws(Exception::class)
    fun create(request: ServletWebRequest)

    /**
     * 检查验证码
     *
     * @param servletWebRequest
     * @throws Exception
     */
    fun validate(servletWebRequest: ServletWebRequest)
}