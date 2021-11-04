package com.sowell.security.exception.auth;


import com.sowell.security.exception.base.SecurityException;
import com.sowell.tool.core.enums.AuthCode;

/**
 * 错误凭据异常
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @date 2021/11/03 17:06
 */
public class BadCredentialsException extends SecurityException {

    public BadCredentialsException() {
        this(null);
    }

    public BadCredentialsException(Throwable cause) {
        this(null, cause);
    }

    public BadCredentialsException(Object responseData) {
        this(responseData, null);
    }

    private BadCredentialsException(Object responseData, Throwable cause) {
        super(AuthCode.PASSWORD_NOT_MATCHES, responseData, cause);
    }
}