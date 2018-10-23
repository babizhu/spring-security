package com.bbz.spring.learning.security.core

import com.bbz.spring.learning.security.core.properties.SecurityProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(SecurityProperties::class)
open class SecurityCoreConfig