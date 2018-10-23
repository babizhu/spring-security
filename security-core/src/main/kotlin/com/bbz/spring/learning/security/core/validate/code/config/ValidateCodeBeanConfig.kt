package com.bbz.spring.learning.security.core.validate.code.config

import com.bbz.spring.learning.security.core.properties.SecurityProperties
import com.bbz.spring.learning.security.core.validate.code.IValidateCodeGenerator
import com.bbz.spring.learning.security.core.validate.code.image.ImageCodeGenerator
import com.bbz.spring.learning.security.core.validate.code.sms.DefaultSmsCodeSender
import com.bbz.spring.learning.security.core.validate.code.sms.SmsCodeSender
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * 验证码相关的扩展点配置。配置在这里的bean，业务系统都可以通过声明同类型或同名的bean来覆盖安全
 * 模块默认的配置。
 *
 */
@Configuration
open class ValidateCodeBeanConfig {
    @Autowired
    private lateinit var securityProperties: SecurityProperties

    /**
     * 缺省的图片验证码图片生成器
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(name = ["imageValidateCodeGenerator"])
    open fun imageValidateCodeGenerator(): IValidateCodeGenerator {
        val codeGenerator = ImageCodeGenerator()
        codeGenerator.securityProperties = securityProperties
        return codeGenerator
    }

    /**
     * 缺省的短信验证码发送器
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(SmsCodeSender::class)
    open fun smsCodeSender(): SmsCodeSender {
        return DefaultSmsCodeSender()
    }

}