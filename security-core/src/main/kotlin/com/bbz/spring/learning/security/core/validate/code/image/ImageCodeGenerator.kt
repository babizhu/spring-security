package com.bbz.spring.learning.security.core.validate.code.image

import com.bbz.spring.learning.security.core.properties.SecurityProperties
import com.bbz.spring.learning.security.core.validate.code.IValidateCodeGenerator
import org.springframework.web.bind.ServletRequestUtils
import org.springframework.web.context.request.ServletWebRequest
import java.awt.Color
import java.awt.Font
import java.awt.image.BufferedImage
import java.util.*

//@Component
//故意省略了上面一行的@Component，而把配置工作放到了@link ValidateCodeBeanConfig，这样有利于有条件的产生此Component
class ImageCodeGenerator: IValidateCodeGenerator {

    lateinit var securityProperties: SecurityProperties

    override fun generate(request: ServletWebRequest): ImageCode {
        val width = ServletRequestUtils.getIntParameter(request.request, "width", securityProperties.code.image.width)
        val height = ServletRequestUtils.getIntParameter(request.request, "height", securityProperties.code.image.height)


        val image = BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
        val graphics = image.graphics

        val random = Random()
        graphics.color = getRandomColor(200, 250)
        graphics.fillRect(0, 0, width, height)
        graphics.font = Font("Times New Roman", Font.ITALIC, 20)
        graphics.color = getRandomColor(160, 200)
        repeat(155) {
            val x = random.nextInt(width)
            val y = random.nextInt(height)
            val xLen = random.nextInt(12)
            val yLen = random.nextInt(12)
            graphics.drawLine(x, y, x + xLen, y + yLen)

        }
        var sRand = ""
        repeat(securityProperties.code.image.length) {
            val rand = random.nextInt(10).toString()
            sRand += rand
            graphics.color = Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110))
            graphics.drawString(rand, 13 * it + 6, 16)

        }

        graphics.dispose()
        return ImageCode(image, sRand, securityProperties.code.image.expireIn)
    }


/**
 * 生成随机背景条纹
 */
private fun getRandomColor(f: Int, bb: Int): Color {
    var fc = f
    var bc = bb
    val random = Random()
    if (fc > 255) {
        fc = 255
    }
    if (bc > 255) {
        bc = 255
    }
    val r = fc + random.nextInt(bc - fc)
    val g = fc + random.nextInt(bc - fc)
    val b = fc + random.nextInt(bc - fc)
    return Color(r, g, b)
}
}