package com.bbz.spring.learning.security.core.validate.code

import java.time.LocalDateTime

open class ValidateCode(val code: String, expireSec: Int) {
    private val expireDate = LocalDateTime.now().plusSeconds(expireSec.toLong())
    val isExpried: Boolean
        get() {
            return LocalDateTime.now().isAfter(expireDate)
        }

}
