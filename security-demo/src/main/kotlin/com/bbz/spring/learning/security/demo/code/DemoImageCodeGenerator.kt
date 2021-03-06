package com.bbz.spring.learning.security.demo.code

import com.bbz.spring.learning.security.core.validate.code.image.ImageCode
import com.bbz.spring.learning.security.core.validate.code.IValidateCodeGenerator
import org.springframework.web.context.request.ServletWebRequest
import java.awt.Color
import java.awt.image.BufferedImage

@Suppress("unused")
//@Component("imageCodeGenerator")
class DemoImageCodeGenerator : IValidateCodeGenerator {
    override fun generate(request: ServletWebRequest): ImageCode {
        println("更高级的图形验证码生成代码")
        val image = BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB)
        val graphics = image.graphics
        graphics.color = Color(255,0,0)
        graphics.dispose()
        return ImageCode(image, "abcd", 60)
    }
}