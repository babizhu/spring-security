package com.bbz.spring.learning.security.demo.validator

import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class MyConstraintValidator : ConstraintValidator<MyConstraint, String> {
    override fun initialize(constraintAnnotation: MyConstraint?) {
        println("my validator init")
    }

    override fun isValid(value: String?, context: ConstraintValidatorContext): Boolean {
        return false
    }

}