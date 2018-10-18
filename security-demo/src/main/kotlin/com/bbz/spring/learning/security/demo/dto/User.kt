package com.bbz.spring.learning.security.demo.dto

import com.bbz.spring.learning.security.demo.validator.MyConstraint
import com.fasterxml.jackson.annotation.JsonView
import java.util.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Past

data class User(
        @JsonView(UserSimpleView::class)
        var id: String?,

        @JsonView(UserSimpleView::class)
        @field:MyConstraint(message = "这是一个校验测试")
        val username: String,

        @JsonView(UserDetaileView::class)
        @field:NotBlank(message = "密码不能为空")
        var password: String?,

        @field:Past(message = "生日必须是过去的时间")
//        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//        @field:JsonFormat(shape=JsonFormat.Shape.NUMBER, locale="zh_CN")
        @JsonView(UserSimpleView::class)
        var birthday: Date = Date(),

        @JsonView(UserSimpleView::class)
        var age: Int = 31) {
    interface UserSimpleView
    interface UserDetaileView : UserSimpleView
}