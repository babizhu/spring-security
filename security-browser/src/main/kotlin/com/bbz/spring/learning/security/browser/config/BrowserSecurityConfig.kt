/**
 *
 */
package com.bbz.spring.learning.security.browser.config

import com.bbz.spring.learning.security.core.authentication.AbstractChannelSecurityConfig
import com.bbz.spring.learning.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig
import com.bbz.spring.learning.security.core.properties.SecurityConstants
import com.bbz.spring.learning.security.core.properties.SecurityProperties
import com.bbz.spring.learning.security.core.validate.code.config.ValidateCodeSecurityConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository
import javax.sql.DataSource

/**
 * 整个Security 配置中心的主干，一切配置都在这列汇总
 */
@Suppress("SpringJavaInjectionPointsAutowiringInspection")
@EnableWebSecurity
open class BrowserSecurityConfig : AbstractChannelSecurityConfig() {

    @Autowired
    private lateinit var securityProperties: SecurityProperties

    @Autowired
    private lateinit var dataSource: DataSource


    @Autowired
    private lateinit var customUserDetailsService: UserDetailsService

    @Autowired
    private lateinit var smsCodeAuthenticationSecurityConfig: SmsCodeAuthenticationSecurityConfig

    @Autowired
    private lateinit var validateCodeSecurityConfig: ValidateCodeSecurityConfig


    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {

        applyPasswordAuthenticationConfig(http)

        http
                .apply(validateCodeSecurityConfig)
                .and()
                .apply(smsCodeAuthenticationSecurityConfig)
                .and()
                .rememberMe()
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(securityProperties.browser.rememberMeSeconds)
                .userDetailsService(customUserDetailsService)
                .and()
                .authorizeRequests()
                .antMatchers(
                        SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
                        SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE,
                        securityProperties.browser.loginPage,
                        SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/*")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .csrf().disable()

    }

    @Bean
    open fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

//    @Bean
    open fun persistentTokenRepository(): PersistentTokenRepository {
        val tokenRepository = JdbcTokenRepositoryImpl()
        tokenRepository.setDataSource(dataSource)
//        		tokenRepository.setCreateTableOnStartup(true)
        return tokenRepository
    }
}