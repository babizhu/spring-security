package com.bbz.spring.learning.security.browser.config

import com.bbz.spring.learning.security.browser.authentication.CustomAuthenticationFailureHandler
import com.bbz.spring.learning.security.browser.authentication.CustomAuthenticationSuccessHandler
import com.bbz.spring.learning.security.core.properties.SecurityProperties
import com.bbz.spring.learning.security.core.validate.code.ValidateCodeFilter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

//@Configuration
@EnableWebSecurity
class BrowserSecurityConfig : WebSecurityConfigurerAdapter() {
    @Autowired
    private lateinit var securityProperties: SecurityProperties

    @Autowired
    private lateinit var customAuthenticationSuccessHandler: CustomAuthenticationSuccessHandler
    @Autowired
    private lateinit var customAuthenticationFailureHandler: CustomAuthenticationFailureHandler


    override fun configure(http: HttpSecurity) {
        val validateCodeFilter = ValidateCodeFilter()
        validateCodeFilter.authenticationFailureHandler = customAuthenticationFailureHandler
        http.addFilterBefore(validateCodeFilter,UsernamePasswordAuthenticationFilter::class.java)
                .formLogin()
                .loginPage("/authentication/require")
                .loginProcessingUrl("/authentication/form")
                .successHandler(customAuthenticationSuccessHandler)
                .failureHandler(customAuthenticationFailureHandler)
//        http.httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers("/authentication/require"
                        , "/error"
                        , securityProperties.browser.loginPage
                        , "/code/image")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .csrf().disable()
//                .permitAll()
    }

//    override fun configure(auth: AuthenticationManagerBuilder) {
////        auth.
//    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

}