package com.bbz.spring.learning.security.core.validate.code

import com.bbz.spring.learning.security.core.properties.SecurityConstants

enum class ValidateCodeType {
    /**
     * 短信验证码
     */
    SMS {
        override fun getParamNameOnValidate(): String {
            return SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_SMS
        }
    },
    /**
     * 图片验证码
     */
    IMAGE {
        override fun getParamNameOnValidate(): String {
            return SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_IMAGE
        }
    };

    /**
     * 校验时从请求中获取的参数的名字
     * @return
     */
    abstract fun getParamNameOnValidate(): String

}