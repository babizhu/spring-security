package com.bbz.spring.learning.security.core.validate.code

import com.bbz.spring.learning.security.core.properties.SecurityProperties
import org.apache.logging.log4j.util.Strings
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.social.connect.web.HttpSessionSessionStrategy
import org.springframework.util.AntPathMatcher
import org.springframework.web.bind.ServletRequestUtils
import org.springframework.web.context.request.ServletWebRequest
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class ValidateCodeFilter : OncePerRequestFilter(), InitializingBean {

    lateinit var authenticationFailureHandler: AuthenticationFailureHandler
    private val sessionStrategy = HttpSessionSessionStrategy()
    private lateinit var urls: HashSet<String>
    private val pathMatcher = AntPathMatcher()
    lateinit var securityProperties: SecurityProperties

    override fun afterPropertiesSet() {
        super.afterPropertiesSet()
        urls = securityProperties.code.image.url.split(",").toHashSet()
        urls.add("/authentication/form")
    }

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val match = urls.any { pathMatcher.match(it, request.requestURI) }
        if (match /**&& request.method == "POST"**/) {
            try {

                validate(ServletWebRequest(request))
            } catch (e: ValidateCodeException) {
                authenticationFailureHandler.onAuthenticationFailure(request, response, e)
                return
            }
        }
        chain.doFilter(request, response)

    }

    private fun validate(request: ServletWebRequest) {
        val codeInSession = sessionStrategy.getAttribute(request, ValidateCodeController.SESSION_KEY) as ImageCode?
        val codeInRequest = ServletRequestUtils.getStringParameter(request.request, "imageCode")
        if (Strings.isBlank(codeInRequest)) {
            throw ValidateCodeException("验证码不能为空")
        }
        if (codeInSession == null) {
            throw ValidateCodeException("Session内的验证码不存在")
        }
        if (codeInSession.isExpried) {
            throw ValidateCodeException("验证码已经过期")

        }
        if (codeInRequest != codeInSession.code) {
            throw ValidateCodeException("验证码不匹配")
        }
        sessionStrategy.removeAttribute(request, ValidateCodeController.SESSION_KEY)
    }


}
