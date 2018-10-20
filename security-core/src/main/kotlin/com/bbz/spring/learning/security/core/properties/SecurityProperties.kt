package com.bbz.spring.learning.security.core.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.NestedConfigurationProperty
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "bbz.security")
class  SecurityProperties {
    //    @NestedConfigurationProperty
    @NestedConfigurationProperty
    var browser = BrowserProperties()

    @NestedConfigurationProperty
    val code = ValidateCodeProperties()


}