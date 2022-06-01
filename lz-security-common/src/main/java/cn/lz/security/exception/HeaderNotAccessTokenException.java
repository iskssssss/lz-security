package cn.lz.security.exception;

import cn.lz.security.exception.base.SecurityException;
import cn.lz.tool.core.enums.RCode;

/**
 * 未携带AccessToken 异常
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 LZ
 * @date 2021/10/22 16:56
 */
public class HeaderNotAccessTokenException extends SecurityException {

	public HeaderNotAccessTokenException() {
		this(null);
	}

	public HeaderNotAccessTokenException(Throwable cause) {
		this(null, cause);
	}

	public HeaderNotAccessTokenException(Object responseData) {
		this(responseData, null);
	}

	private HeaderNotAccessTokenException(Object responseData, Throwable cause) {
		super(RCode.HEADER_NOT_ACCESS_TOKEN, responseData, cause);
	}
}
