package com.bbz.spring.learning.security.core.validate.code

import com.bbz.spring.learning.security.core.properties.SecurityProperties
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class ValidateCodeBeanConfig{
    @Autowired
    private lateinit var securityProperties: SecurityProperties
    @Bean
    @ConditionalOnMissingBean(name=["imageCodeGenerator"])
    open fun imageCodeGenerator():ValidateCodeGenerator{
        val codeGenerator = ImageCodeGenerator()
        codeGenerator.securityProperties = securityProperties
        return codeGenerator
    }

}