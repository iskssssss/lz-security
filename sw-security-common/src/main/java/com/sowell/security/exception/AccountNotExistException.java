package com.sowell.security.exception;

import com.sowell.security.enums.RCode;

/**
 * @Version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/08/19 15:24
 */
public class AccountNotExistException extends SecurityException {

	public AccountNotExistException() {
		this(null);
	}

	public AccountNotExistException(Throwable cause) {
		this(null, cause);
	}

	public AccountNotExistException(Object responseData) {
		this(responseData, null);
	}

	private AccountNotExistException(Object responseData, Throwable cause) {
		super(RCode.TOKEN_EXPIRE.getCode(), RCode.TOKEN_EXPIRE.getMessage(), responseData, cause);
	}
}
