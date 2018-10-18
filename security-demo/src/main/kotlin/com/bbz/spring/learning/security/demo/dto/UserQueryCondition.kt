package com.bbz.spring.learning.security.demo.dto

import io.swagger.annotations.ApiModelProperty

data class UserQueryCondition (
        @field:ApiModelProperty("待查询待用户名")
        val username:String="",
        val age:Int =80
)