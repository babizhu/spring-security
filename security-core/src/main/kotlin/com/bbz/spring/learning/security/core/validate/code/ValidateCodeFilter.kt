package com.bbz.spring.learning.security.core.validate.code

import com.bbz.spring.learning.security.core.properties.SecurityConstants
import com.bbz.spring.learning.security.core.properties.SecurityProperties
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.stereotype.Component
import org.springframework.util.AntPathMatcher
import org.springframework.web.context.request.ServletWebRequest
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


/**
 * 校验验证码的过滤器
 *
 */
@Component("validateCodeFilter")
open class ValidateCodeFilter : OncePerRequestFilter(), InitializingBean {

    /**
     * 验证码校验失败处理器
     */
    @Autowired
    private lateinit var customAuthenticationFailureHandler: AuthenticationFailureHandler
    /**
     * 系统配置信息
     */
    @Autowired
    private lateinit var securityProperties: SecurityProperties
    /**
     * 系统中的校验码处理器
     */
    @Autowired
    private lateinit var validateCodeProcessorHolder: ValidateCodeProcessorHolder
    /**
     * 存放所有需要校验验证码的url
     */
    private val urlMap = HashMap<String, ValidateCodeType>()
    /**
     * 验证请求url与配置的url是否匹配的工具类
     */
    private val pathMatcher = AntPathMatcher()

    /**
     * 初始化要拦截的url配置信息
     */
    @Throws(ServletException::class)
    override fun afterPropertiesSet() {
        super.afterPropertiesSet()

        urlMap[SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM] = ValidateCodeType.IMAGE
        addUrlToMap(securityProperties.code.image.url, ValidateCodeType.IMAGE)

        urlMap[SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE] = ValidateCodeType.SMS
        addUrlToMap(securityProperties.code.sms.url, ValidateCodeType.SMS)
    }

    /**
     * 讲系统中配置的需要校验验证码的URL根据校验的类型放入map
     *
     * @param urlString
     * @param type
     */
    private fun addUrlToMap(urlString: String, type: ValidateCodeType) {
        if (!urlString.isNullOrBlank()) {
            val urls = urlString.split(",")
            for (url in urls) {
                urlMap[url] = type
            }
        }
    }

    /*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.springframework.web.filter.OncePerRequestFilter#doFilterInternal(
	 * javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain)
	 */
    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {

        val type = getValidateCodeType(request)
        if (type != null) {
            logger.info("校验请求(" + request.requestURI + ")中的验证码,验证码类型" + type)
            try {
                validateCodeProcessorHolder.findValidateCodeProcessor(type).validate(ServletWebRequest(request, response))
                logger.info("验证码校验通过")
            } catch (exception: ValidateCodeException) {
                customAuthenticationFailureHandler.onAuthenticationFailure(request, response, exception)
                return
            }

        }

        chain.doFilter(request, response)

    }

    /**
     * 获取校验码的类型，如果当前请求不需要校验，则返回null
     *
     * @param request
     * @return
     */
    private fun getValidateCodeType(request: HttpServletRequest): ValidateCodeType? {
        var result: ValidateCodeType? = null
        if (request.method.toLowerCase() != "get" ) {//如果要验证非登录的url，就不能加这个判断了，因为非登录url的访问的方法可能是Method.Get
            val urls = urlMap.keys
            for (url in urls) {
                if (pathMatcher.match(url, request.requestURI)) {
                    result = urlMap[url]
                }
            }
        }
        return result
    }

}