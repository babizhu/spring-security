//package com.bbz.spring.learning.security.core.authentication
//
//import org.slf4j.LoggerFactory
//import org.springframework.security.core.userdetails.UsernameNotFoundException
//import org.springframework.security.core.userdetails.UserDetails
//import org.springframework.security.core.userdetails.UserDetailsService
//
//
///**
// *
// * 默认的 UserDetailsService 实现
// *
// * 不做任何处理，只在控制台打印一句日志，然后抛出异常，提醒业务系统自己配置 UserDetailsService。
// *
// * @author zhailiang
// */
//class DefaultUserDetailsService : UserDetailsService {
//
//    private val logger = LoggerFactory.getLogger(javaClass)
//
//    /*
//	 * (non-Javadoc)
//	 *
//	 * @see org.springframework.security.core.userdetails.UserDetailsService#
//	 * loadUserByUsername(java.lang.String)
//	 */
//    @Throws(UsernameNotFoundException::class)
//    override fun loadUserByUsername(username: String): UserDetails {
//        logger.warn("请配置 UserDetailsService 接口的实现.")
//        throw UsernameNotFoundException(username)
//    }
//
//}