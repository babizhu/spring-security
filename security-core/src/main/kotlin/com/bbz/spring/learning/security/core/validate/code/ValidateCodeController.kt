package com.bbz.spring.learning.security.core.validate.code


import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.springframework.social.connect.web.HttpSessionSessionStrategy

@RestController
class ValidateCodeController {

    companion object {
        private const val SESSION_KEY = "sesssion_key_image_code"
    }

    private val sessionStrategy = HttpSessionSessionStrategy()

    @GetMapping("/code/image")
    fun createCode(request: HttpServletRequest, response: HttpServletResponse) {
        var imageCode = createImageCode(request)
    }

    private fun createImageCode(request: HttpServletRequest): ImageCode {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}