package com.bbz.spring.learning.security.core.validate.code


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.social.connect.web.HttpSessionSessionStrategy
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.ServletWebRequest
import javax.imageio.ImageIO
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@RestController
class ValidateCodeController {

    companion object {
        const val SESSION_KEY = "sesssion_key_image_code"
    }

    private val sessionStrategy = HttpSessionSessionStrategy()

    @Autowired
    private lateinit var imageCodeGenerator: ValidateCodeGenerator

    @GetMapping("/code/image")
    fun createCode(request: HttpServletRequest, response: HttpServletResponse) {
        val servletWebRequest = ServletWebRequest(request)
        val imageCode = imageCodeGenerator.generator(servletWebRequest)
        sessionStrategy.setAttribute(servletWebRequest, SESSION_KEY, imageCode)
        ImageIO.write(imageCode.image, "JPEG", response.outputStream)
    }


}