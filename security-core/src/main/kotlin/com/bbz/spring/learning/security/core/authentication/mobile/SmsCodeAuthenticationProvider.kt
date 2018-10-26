package com.bbz.spring.learning.security.core.authentication.mobile

import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.InternalAuthenticationServiceException
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetailsService

class SmsCodeAuthenticationProvider : AuthenticationProvider {
    lateinit var userDetailsService: UserDetailsService
    override fun supports(authentication: Class<*>?): Boolean {
        return SmsCodeAuthenticationToken::class.java.isAssignableFrom(authentication)
    }

    override fun authenticate(authentication: Authentication): Authentication {
        val user = userDetailsService.loadUserByUsername(authentication.principal as String) ?: throw InternalAuthenticationServiceException("无法获取用户信息")
        val authenticationResult = SmsCodeAuthenticationToken(user, user.authorities)
        authenticationResult.details = authentication.details
        return authenticationResult
    }
}