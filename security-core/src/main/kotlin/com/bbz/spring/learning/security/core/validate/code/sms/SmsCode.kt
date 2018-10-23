package com.bbz.spring.learning.security.core.validate.code.sms

import com.bbz.spring.learning.security.core.validate.code.ValidateCode


class SmsCode(code: String, expireSec: Int) : ValidateCode(code, expireSec)