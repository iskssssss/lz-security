package com.sowell.security.context.model;

import com.sowell.security.enums.RCode;
import com.sowell.security.exception.SecurityException;

/**
 * @Version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/17 10:33
 */
public abstract class BaseResponse<T> {

	protected T response;

	public BaseResponse() {

	}
	public BaseResponse(T response) {
		this.response = response;
	}

	public final T getResponse() {
		if (response == null){
			throw new SecurityException(RCode.REQUEST_ERROR.getCode(), RCode.REQUEST_ERROR.getMessage());
		}
		return response;
	}

	public final void setResponse(T response) {
		this.response = response;
	}

	public abstract BaseResponse<T> setHeader(String name, String value);

	public abstract void print(String message);

	public abstract void print(byte[] bytes);

	public abstract void print(byte[] bytes, int off, int len);

	public abstract byte[] getResponseDataBytes();

	public abstract int getStatus();

	public abstract void setStatus(int value);
}
