package com.bbz.spring.learning.security.core.validate.code.image

import com.bbz.spring.learning.security.core.validate.code.impl.AbstractValidateCodeProcessor
import org.springframework.stereotype.Component
import org.springframework.web.context.request.ServletWebRequest
import javax.imageio.ImageIO
import javax.naming.AuthenticationException

@Component("imageValidateCodeProcessor")
class ImageCodeProcessor: AbstractValidateCodeProcessor<ImageCode>() {
    override fun send(request: ServletWebRequest, validateCode: ImageCode) {
        val response = request.response ?: throw AuthenticationException("找不到ServletWebRequest中的response")
        ImageIO.write(validateCode.image,"JPEG",response.outputStream)
    }
}