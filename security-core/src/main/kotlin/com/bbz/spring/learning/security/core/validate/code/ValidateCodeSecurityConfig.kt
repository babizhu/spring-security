package com.bbz.spring.learning.security.core.validate.code

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.config.annotation.SecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter
import org.springframework.stereotype.Component
import javax.servlet.Filter

@Component("validateCodeSecurityConfig")
class ValidateCodeSecurityConfig : SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>() {

    @Autowired
    private lateinit var validateCodeFilter: Filter

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.addFilterBefore(validateCodeFilter, AbstractPreAuthenticatedProcessingFilter::class.java)
    }
}