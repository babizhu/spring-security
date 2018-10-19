package com.bbz.spring.learning.security.core.validate.code

import java.awt.image.BufferedImage
import java.time.LocalDateTime

/**
 * 图形验证码
 */
class ImageCode (val image: BufferedImage,val code: String,expireSec: Int){
    private val expireDate = LocalDateTime.now().plusSeconds(expireSec.toLong())
    val isExpried: Boolean
    get() {
        return LocalDateTime.now().isAfter(expireDate)
    }

}