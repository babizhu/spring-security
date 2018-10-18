package com.bbz.spring.learning.security.core.validate.code

import java.awt.image.BufferedImage
import java.time.LocalDateTime

/**
 * 图形验证码
 */
class ImageCode (val image: BufferedImage,val code: String,expireSec: Int){
    val expireDate: LocalDateTime = LocalDateTime.now().plusSeconds(expireSec as Long)

}