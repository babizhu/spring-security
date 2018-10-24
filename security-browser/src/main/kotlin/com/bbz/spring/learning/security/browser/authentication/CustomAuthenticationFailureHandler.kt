package com.bbz.spring.learning.security.browser.authentication

import com.bbz.spring.learning.security.browser.support.SimpleResponse
import com.bbz.spring.learning.security.core.properties.LoginType
import com.bbz.spring.learning.security.core.properties.SecurityProperties
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class CustomAuthenticationFailureHandler: SimpleUrlAuthenticationFailureHandler() {

    companion object {
         private val log = LoggerFactory.getLogger(CustomAuthenticationFailureHandler::class.java)
    }

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    private lateinit var securityProperties: SecurityProperties

    override fun onAuthenticationFailure(request: HttpServletRequest, response: HttpServletResponse, exception: AuthenticationException) {

        log.info("登录失败")
        if(securityProperties.browser.loginType == LoginType.JSON) {
            response.contentType = "application/json;charset=UTF-8"
            response.status = HttpStatus.INTERNAL_SERVER_ERROR.value()
            response.writer.write(objectMapper.writeValueAsString(SimpleResponse(exception.message!!)))
//            response.writer.write(exception.message)
        }else{
            super.onAuthenticationFailure(request, response, exception)
        }
    }
}