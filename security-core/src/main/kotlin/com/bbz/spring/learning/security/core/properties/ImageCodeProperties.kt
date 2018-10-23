package com.bbz.spring.learning.security.core.properties


class ImageCodeProperties : AbstractCodeProperties() {
    init {
        //AbstractCodeProperties中code的默认长度为6（适用于smsCode），不适用于图形验证码，所以这里修改为4
        length = 4
    }

    var width = 67
    var height = 23


}