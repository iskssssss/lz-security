package com.sowell.security.exception.auth;

import com.sowell.security.exception.base.SecurityException;
import com.sowell.tool.core.enums.AuthCode;

/**
 * 帐户过期异常
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 LZ
 * @date 2021/11/03 17:06
 */
public class AccountExpiredException extends SecurityException {

	public AccountExpiredException() {
		this(null);
	}

	public AccountExpiredException(Throwable cause) {
		this(null, cause);
	}

	public AccountExpiredException(Object responseData) {
		this(responseData, null);
	}

	private AccountExpiredException(Object responseData, Throwable cause) {
		super(AuthCode.ACCOUNT_EXPIRED, responseData, cause);
	}
}
