package com.sowell.security.exception;

/**
 * @Version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/08/19 15:24
 */
public class AccountExpiredException extends SecurityException {

	public AccountExpiredException() {
		super("Token已过期");
	}

	public AccountExpiredException(Object responseData) {
		super(responseData);
	}

	public AccountExpiredException(String message, Object responseData) {
		super(message, responseData);
	}
}
