package com.bbz.spring.learning.security.demo.exception

import java.lang.RuntimeException

class UserNotExistException(val id:String) : RuntimeException("user $id not found ")