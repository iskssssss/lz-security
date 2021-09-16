package com.sowell.security.exception;

/**
 * @Version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/08/19 15:24
 */
public class AccountNotExistException extends SecurityException {

	private final static String MESSAGE = "AccessToken不存在或已过期";

	public AccountNotExistException() {
		super(MESSAGE);
	}

	public AccountNotExistException(Object responseData) {
		super(MESSAGE, responseData);
	}

	public AccountNotExistException(String message, Object responseData) {
		super(message, responseData);
	}
}
