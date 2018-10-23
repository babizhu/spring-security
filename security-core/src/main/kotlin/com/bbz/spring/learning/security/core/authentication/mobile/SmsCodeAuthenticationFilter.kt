package com.bbz.spring.learning.security.core.authentication.mobile

import com.bbz.spring.learning.security.core.properties.SecurityConstants
import org.springframework.security.authentication.AuthenticationServiceException
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.util.Assert
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class SmsCodeAuthenticationFilter :
        AbstractAuthenticationProcessingFilter(AntPathRequestMatcher(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE, "POST")) {

    /**
     * Sets the parameter name which will be used to obtain the mobile from the login
     * request.
     */
    var mobileParameter = SecurityConstants.DEFAULT_PARAMETER_NAME_MOBILE
        set(mobileParameter) {
            Assert.hasText(mobileParameter, "Mobile parameter must not be empty or null")
            field = mobileParameter
        }

    private var postOnly = true

    // ~ Methods
    // ========================================================================================================

    @Throws(AuthenticationException::class)
    override fun attemptAuthentication(request: HttpServletRequest,
                                       response: HttpServletResponse): Authentication {
        if (postOnly && request.method != "POST") {
            throw AuthenticationServiceException(
                    "Authentication method not supported: " + request.method)
        }

        val mobile: String = obtainMobile(request).trim()


        val authRequest = SmsCodeAuthenticationToken(mobile)

        // Allow subclasses to set the "details" property
        setDetails(request, authRequest)

        return this.authenticationManager.authenticate(authRequest)
    }


    /**
     * Enables subclasses to override the composition of the username, such as by
     * including additional values and a separator.
     *
     * @param request so that request attributes can be retrieved
     *
     * @return the username that will be presented in the `Authentication`
     * request token to the `AuthenticationManager`
     */
    private fun obtainMobile(request: HttpServletRequest): String {
        return request.getParameter(mobileParameter) ?: return ""
    }

    /**
     * Provided so that subclasses may configure what is put into the authentication
     * request's details property.
     *
     * @param request that an authentication request is being created for
     * @param authRequest the authentication request object that should have its details
     * set
     */
    private fun setDetails(request: HttpServletRequest,
                             authRequest: SmsCodeAuthenticationToken) {
        authRequest.details = authenticationDetailsSource.buildDetails(request)
    }

    /**
     * Defines whether only HTTP POST requests will be allowed by this filter. If set to
     * true, and an authentication request is received which is not a POST request, an
     * exception will be raised immediately and authentication will not be attempted. The
     * <tt>unsuccessfulAuthentication()</tt> method will be called as if handling a failed
     * authentication.
     *
     *
     * Defaults to <tt>true</tt> but may be overridden by subclasses.
     */
    fun setPostOnly(postOnly: Boolean) {
        this.postOnly = postOnly
    }

}