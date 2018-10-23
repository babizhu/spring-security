package com.bbz.spring.learning.security.core.properties

class BrowserProperties{
    var loginPage:String = SecurityConstants.DEFAULT_LOGIN_PAGE_URL
    var loginType = LoginType.JSON
    var rememberMeSeconds = 3600
}
