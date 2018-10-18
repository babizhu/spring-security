package com.bbz.spring.learning.security.browser.authentication



import com.bbz.spring.learning.security.core.properties.LoginType
import com.bbz.spring.learning.security.core.properties.SecurityProperties
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired

import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component("customAuthenticationSuccessHandler")
class CustomAuthenticationSuccessHandler: SavedRequestAwareAuthenticationSuccessHandler() {

    companion object {
         private val log = LoggerFactory.getLogger(CustomAuthenticationSuccessHandler::class.java)
    }

    @Autowired
    private lateinit var securityProperties: SecurityProperties

    @Autowired
    private lateinit var objectMapper: ObjectMapper
    override fun onAuthenticationSuccess(request: HttpServletRequest, response: HttpServletResponse, authentication: Authentication) {
        log.info("登录成功")
        if(securityProperties.browser.loginType==LoginType.JSON){

            response.contentType = "application/json;charset=UTF-8"
            response.writer.write(objectMapper.writeValueAsString(authentication))
        }else {
            super.onAuthenticationSuccess(request, response, authentication)
        }

    }
}