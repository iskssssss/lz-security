package com.sowell.security.exception.auth;


import com.sowell.security.exception.base.SecurityException;
import com.sowell.tool.core.enums.AuthCode;

/**
 * 凭据过期异常
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @date 2021/11/03 17:06
 */
public class CredentialsExpiredException extends SecurityException {

    public CredentialsExpiredException() {
        this(null);
    }

    public CredentialsExpiredException(Throwable cause) {
        this(null, cause);
    }

    public CredentialsExpiredException(Object responseData) {
        this(responseData, null);
    }

    private CredentialsExpiredException(Object responseData, Throwable cause) {
        super(AuthCode.CREDENTIALS_EXPIRED, responseData, cause);
    }
}
