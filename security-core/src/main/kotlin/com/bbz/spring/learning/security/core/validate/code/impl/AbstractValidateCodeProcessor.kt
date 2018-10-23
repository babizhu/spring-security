package com.bbz.spring.learning.security.core.validate.code.impl

import com.bbz.spring.learning.security.core.validate.code.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.social.connect.web.HttpSessionSessionStrategy
import org.springframework.web.bind.ServletRequestUtils
import org.springframework.web.context.request.ServletWebRequest


/**
 * 抽象的图片验证码处理器
 * @author zhailiang
 */
abstract class AbstractValidateCodeProcessor<C : ValidateCode> : IValidateCodeProcessor {
private val SESSION_KEY_PREFIX = "session"
    /**
     * 操作session的工具类
     */
    private val sessionStrategy = HttpSessionSessionStrategy()
    /**
     * 收集系统中所有的 [IValidateCodeGenerator] 接口的实现。
     */
    @Autowired
    private val validateCodeGenerators: Map<String, IValidateCodeGenerator>? = null

    /*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.imooc.security.core.validate.code.ValidateCodeProcessor#create(org.
	 * springframework.web.context.request.ServletWebRequest)
	 */
    @Throws(Exception::class)
    override fun create(request: ServletWebRequest) {
        val validateCode = generate(request)
        save(request, validateCode)
        send(request, validateCode)
    }

    /**
     * 生成校验码
     *
     * @param request
     * @return
     */
    private fun generate(request: ServletWebRequest): C {
        val type = getValidateCodeType(request).toString().toLowerCase()
        val generatorName = type + IValidateCodeGenerator::class.java.simpleName.substring(1)
        val validateCodeGenerator = validateCodeGenerators!![generatorName]
                ?: throw ValidateCodeException("验证码生成器" + generatorName + "不存在")
        return validateCodeGenerator.generate(request) as C
    }

    /**
     * 保存校验码
     *
     * @param request
     * @param validateCode
     */
    private fun save(request: ServletWebRequest, validateCode: C) {
        sessionStrategy.setAttribute(request, getSessionKey(request), validateCode)
    }

    /**
     * 构建验证码放入session时的key
     *
     * @param request
     * @return
     */
    private fun getSessionKey(request: ServletWebRequest): String {
        return SESSION_KEY_PREFIX + getValidateCodeType(request).toString().toUpperCase()
    }

    /**
     * 发送校验码，由子类实现
     *
     * @param request
     * @param validateCode
     * @throws Exception
     */
    @Throws(Exception::class)
    protected abstract fun send(request: ServletWebRequest, validateCode: C)

    /**
     * 根据请求的url获取校验码的类型
     *
     * @param request
     * @return
     */
    private fun getValidateCodeType(request: ServletWebRequest): ValidateCodeType {
        val type =javaClass.simpleName.substringBefore( "CodeProcessor")
        return ValidateCodeType.valueOf(type.toUpperCase())
    }

    override fun validate(servletWebRequest: ServletWebRequest) {

        val processorType = getValidateCodeType(servletWebRequest)
        val sessionKey = getSessionKey(servletWebRequest)

        val codeInSession = sessionStrategy.getAttribute(servletWebRequest, sessionKey) as C?

        val codeInRequest: String = ServletRequestUtils.getStringParameter(servletWebRequest.request,
                processorType.getParamNameOnValidate()) ?: throw ValidateCodeException("获取验证码的值失败")
//        try {
//            codeInRequest =
//        } catch (e: ServletRequestBindingException) {
//            throw ValidateCodeException("获取验证码的值失败")
//        }

        if (codeInRequest.isBlank()) {
            throw ValidateCodeException(processorType.toString() + "验证码的值不能为空")
        }

        if (codeInSession == null) {
            throw ValidateCodeException(processorType.toString() + "验证码不存在")
        }

        if (codeInSession.isExpried) {
            sessionStrategy.removeAttribute(servletWebRequest, sessionKey)
            throw ValidateCodeException(processorType.toString() + "验证码已过期")
        }

        if (codeInSession.code != codeInRequest) {
            throw ValidateCodeException(processorType.toString() + "验证码不匹配")
        }

        sessionStrategy.removeAttribute(servletWebRequest, sessionKey)
    }

}