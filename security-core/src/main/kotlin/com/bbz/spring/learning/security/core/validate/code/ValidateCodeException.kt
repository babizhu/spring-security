package com.bbz.spring.learning.security.core.validate.code

import org.springframework.security.core.AuthenticationException

class ValidateCodeException(msg:String): AuthenticationException(msg)