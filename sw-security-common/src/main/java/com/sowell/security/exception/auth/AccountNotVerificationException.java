package com.sowell.security.exception.auth;


import com.sowell.security.exception.base.SecurityException;
import com.sowell.tool.core.enums.AuthCode;

/**
 * 帐户未验证异常
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @date 2021/11/03 17:06
 */
public class AccountNotVerificationException extends SecurityException {

    public AccountNotVerificationException() {
        this(null);
    }

    public AccountNotVerificationException(Throwable cause) {
        this(null, cause);
    }

    public AccountNotVerificationException(Object responseData) {
        this(responseData, null);
    }

    private AccountNotVerificationException(Object responseData, Throwable cause) {
        super(AuthCode.ACCOUNT_NOT_VERIFICATION, responseData, cause);
    }
}