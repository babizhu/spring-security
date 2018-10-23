//package com.bbz.spring.learning.security.core.authentication
//
//import com.bbz.spring.learning.security.core.properties.SecurityConstants
//import org.springframework.security.config.annotation.web.builders.HttpSecurity
//import org.springframework.security.web.authentication.AuthenticationFailureHandler
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler
//import org.springframework.stereotype.Component
//
//
///**
// * 表单登录配置
// *
// * @author zhailiang
// */
//@Component
//class FormAuthenticationConfig {
//
//    @Autowired
//    protected lateinit var customAuthenticationSuccessHandler: AuthenticationSuccessHandler
//
//    @Autowired
//    protected lateinit var customAuthenticationFailureHandler: AuthenticationFailureHandler
//
//    @Throws(Exception::class)
//    fun configure(http: HttpSecurity) {
//        http.formLogin()
//                .loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
//                .loginProcessingUrl(SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_FORM)
//                .successHandler(customAuthenticationSuccessHandler)
//                .failureHandler(customAuthenticationFailureHandler)
//    }
//
//}