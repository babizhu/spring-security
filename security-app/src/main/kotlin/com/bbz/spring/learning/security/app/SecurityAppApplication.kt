package com.bbz.spring.learning.security.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SecurityAppApplication

fun main(args: Array<String>) {
    runApplication<SecurityAppApplication>(*args)
}
