package com.sowell.security.exception.auth;

import com.sowell.security.exception.base.SecurityException;
import com.sowell.tool.core.enums.AuthCode;

/**
 * 帐户锁定异常
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 LZ
 * @date 2021/11/03 17:06
 */
public class AccountLockedException extends SecurityException {

    public AccountLockedException() {
        this(null);
    }

    public AccountLockedException(Throwable cause) {
        this(null, cause);
    }

    public AccountLockedException(Object responseData) {
        this(responseData, null);
    }

    private AccountLockedException(Object responseData, Throwable cause) {
        super(AuthCode.ACCOUNT_LOCKED, responseData, cause);
    }
}
