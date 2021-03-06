//package com.bbz.spring.learning.security.browser.authorize
//
//import com.bbz.spring.learning.security.core.authorize.AuthorizeConfigProvider
//import org.springframework.core.annotation.Order
//import org.springframework.http.HttpMethod
//import org.springframework.security.config.annotation.web.builders.HttpSecurity
//import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer
//import org.springframework.stereotype.Component
//
//
///**
// * 浏览器环境默认的授权配置，对常见的静态资源，如js,css，图片等不验证身份
// *
// * @author zhailiang
// */
//@Component
//@Order(Integer.MIN_VALUE)
//class BrowserAuthorizeConfigProvider : AuthorizeConfigProvider {
//
//    override fun config(config: ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry): Boolean {
//        config.antMatchers(HttpMethod.GET,
//                "/**/*.js",
//                "/**/*.css",
//                "/**/*.jpg",
//                "/**/*.png",
//                "/**/*.gif").permitAll()
//        return false
//    }
//
//}