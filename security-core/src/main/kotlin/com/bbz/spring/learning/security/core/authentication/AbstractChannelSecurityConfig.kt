package com.bbz.spring.learning.security.core.authentication

import com.bbz.spring.learning.security.core.properties.SecurityConstants
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter


/**
 * @author zhailiang
 */

open class AbstractChannelSecurityConfig : WebSecurityConfigurerAdapter() {

    @Autowired
    protected lateinit var customAuthenticationSuccessHandler: AuthenticationSuccessHandler

    @Autowired
    protected lateinit var customAuthenticationFailureHandler: AuthenticationFailureHandler

    @Throws(Exception::class)
    protected fun applyPasswordAuthenticationConfig(http: HttpSecurity) {
        http.formLogin()
                .loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
                .loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)
                .successHandler(customAuthenticationSuccessHandler)
                .failureHandler(customAuthenticationFailureHandler)
    }

}