package cn.lz.security.exception.auth;

import cn.lz.security.exception.base.SecurityException;
import cn.lz.tool.core.enums.AuthCode;

/**
 * 帐户不存在异常
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 LZ
 * @date 2021/11/03 17:06
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
		super(AuthCode.ACCOUNT_NOT_EXISTS, responseData, cause);
	}
}
