package com.sowell.security.exception;

import com.sowell.tool.core.enums.RCode;

/**
 * @Version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/08/19 15:24
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
		super(RCode.TOKEN_EXPIRE.getCode(), RCode.TOKEN_EXPIRE.getMessage(), responseData, cause);
	}
}
