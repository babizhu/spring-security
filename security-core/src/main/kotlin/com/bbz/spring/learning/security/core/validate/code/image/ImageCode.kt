package com.bbz.spring.learning.security.core.validate.code.image

import com.bbz.spring.learning.security.core.validate.code.ValidateCode
import java.awt.image.BufferedImage

/**
 * 图形验证码
 */
class ImageCode( val image: BufferedImage, code: String, expireSec: Int) : ValidateCode(code, expireSec)