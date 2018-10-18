package com.bbz.spring.learning.security

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import springfox.documentation.swagger2.annotations.EnableSwagger2

@SpringBootApplication
@EnableSwagger2
class SecurityDemoApplication

fun main(args: Array<String>) {
//    Test.test()
    runApplication<SecurityDemoApplication>(*args)
}
