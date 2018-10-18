package com.bbz.spring.learning.security.demo.web.config

import com.bbz.spring.learning.security.demo.web.filter.TimeFilter
import com.bbz.spring.learning.security.demo.web.interceptor.TimeInterceptor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import javax.servlet.Filter

//@Configuration
@Suppress("unused")
/**
 * timeFilter()方法演示了
 * 如何添加没有@Component注解的Filter
 * 这么做额外还有个好处是可以指定url Pattern
 */
class WebConfig : WebMvcConfigurer {
    @Autowired
    lateinit var  timeInterCeptor: TimeInterceptor
    @Bean
    fun timeFilter(): FilterRegistrationBean<Filter> {
        val registrationBean = FilterRegistrationBean<Filter>()
        registrationBean.filter = TimeFilter()
        registrationBean.urlPatterns = listOf("/*")
        return registrationBean
    }

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(timeInterCeptor)
    }

    /**
     * 异步请求拦截器
     */
    override fun configureAsyncSupport(configurer: AsyncSupportConfigurer) {
//        configurer.registerCallableInterceptors()
    }
}