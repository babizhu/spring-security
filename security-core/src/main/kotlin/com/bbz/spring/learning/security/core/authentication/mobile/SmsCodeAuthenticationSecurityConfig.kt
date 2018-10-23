package com.bbz.spring.learning.security.core.authentication.mobile

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.SecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.core.userdetails.UserDetailsService

import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
open class SmsCodeAuthenticationSecurityConfig : SecurityConfigurerAdapter<DefaultSecurityFilterChain,HttpSecurity>() {
    @Autowired
    private lateinit var customAuthenticationFailureHandler: AuthenticationFailureHandler

    @Autowired
    private lateinit var customAuthenticationSuccessHandler: AuthenticationSuccessHandler

    @Autowired
    private lateinit var customUserDetailsService: UserDetailsService

    override  fun configure(http: HttpSecurity) {
        val smsCodeAuthenticationFilter = SmsCodeAuthenticationFilter()
        smsCodeAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager::class.java))
        smsCodeAuthenticationFilter.setAuthenticationSuccessHandler(customAuthenticationSuccessHandler)
        smsCodeAuthenticationFilter.setAuthenticationFailureHandler(customAuthenticationFailureHandler)

        val smsCodeAuthenticationProvider = SmsCodeAuthenticationProvider()
        smsCodeAuthenticationProvider.userDetailsService = customUserDetailsService


        http.authenticationProvider(smsCodeAuthenticationProvider)
                .addFilterAfter(smsCodeAuthenticationFilter,UsernamePasswordAuthenticationFilter::class.java)
    }
}