package com.bbz.spring.learning.security.browser.service

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService : UserDetailsService {

    companion object {
        private val log = LoggerFactory.getLogger(CustomUserDetailsService::class.java)
    }

    @Autowired
    private lateinit var passwordEncoder :PasswordEncoder

    override fun loadUserByUsername(username: String): UserDetails {
        log.info("登录用户名 : $username")

        return User(username, passwordEncoder.encode("123456"),
                true,
                true,
                true,
                true,
                AuthorityUtils.commaSeparatedStringToAuthorityList("admin"))
    }
}