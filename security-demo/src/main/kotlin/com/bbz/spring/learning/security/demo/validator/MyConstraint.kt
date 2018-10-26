package com.bbz.spring.learning.security.demo.validator

//import java.lang.annotation.Retention
//import java.lang.annotation.Retention

import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass

//@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER, AnnotationTarget.FIELD)
@Target(AnnotationTarget.FIELD,AnnotationTarget.FUNCTION)
@Retention()
@Constraint(validatedBy = [MyConstraintValidator::class])
annotation class MyConstraint(val message: String, val groups: Array<KClass<*>> = [], val payload: Array<KClass<out Payload>> = [])
