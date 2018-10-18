package com.bbz.spring.learning.security.browser.controller

import com.bbz.spring.learning.security.browser.support.SimpleResponse
import com.bbz.spring.learning.security.core.properties.SecurityProperties
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.web.DefaultRedirectStrategy
import org.springframework.security.web.savedrequest.HttpSessionRequestCache
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@RestController
class BrowserSecurityController {
    companion object {
        private val log = LoggerFactory.getLogger(BrowserSecurityController::class.java)
    }

    private val requestCache = HttpSessionRequestCache()
    private val redirectStrategy = DefaultRedirectStrategy()
    @Autowired
    lateinit var securityProperties: SecurityProperties

    @RequestMapping("/authentication/require")
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    fun requireAuthentication(request: HttpServletRequest, response: HttpServletResponse): SimpleResponse {
        val saveRequest = requestCache.getRequest(request, response)
        if (saveRequest != null) {
            val targetUrl = saveRequest.redirectUrl
            log.info("引发跳转的请求是：$targetUrl")
            if (targetUrl.endsWith("html")) {
                redirectStrategy.sendRedirect(request, response, securityProperties.browser.loginPage)
            }
        }
        return SimpleResponse("访问的服务需要身份认证，请引导用户到登录页面")
    }
}