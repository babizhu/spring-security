package com.bbz.spring.learning.security.browser.controller

import com.bbz.spring.learning.security.browser.support.SimpleResponse
import com.bbz.spring.learning.security.core.properties.SecurityConstants
import com.bbz.spring.learning.security.core.properties.SecurityProperties
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.web.DefaultRedirectStrategy
import org.springframework.security.web.savedrequest.HttpSessionRequestCache
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.io.IOException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@RestController
class BrowserSecurityController {

    private val logger = LoggerFactory.getLogger(javaClass)

    private val requestCache = HttpSessionRequestCache()

    private val redirectStrategy = DefaultRedirectStrategy()

    @Autowired
    private lateinit var securityProperties: SecurityProperties

    /**
     * 当需要身份认证时，跳转到这里
     *
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    @Throws(IOException::class)
    fun requireAuthentication(request: HttpServletRequest, response: HttpServletResponse): SimpleResponse {

        val savedRequest = requestCache.getRequest(request, response)

        if (savedRequest != null) {
            val targetUrl = savedRequest.redirectUrl
            logger.info("引发跳转的请求是:$targetUrl")
            if (targetUrl.endsWith(".html",true)) {
                redirectStrategy.sendRedirect(request, response, securityProperties.browser.loginPage)
            }
        }

        return SimpleResponse("访问的服务需要身份认证，请引导用户到登录页")
    }

}