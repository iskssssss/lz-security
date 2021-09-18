package com.sowell.security.context.model;

import com.sowell.security.enums.RCode;
import com.sowell.security.exception.SecurityException;

import java.util.List;

/**
 * @Version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/17 10:33
 */
public abstract class BaseRequest<T> {

	protected T request;

	public BaseRequest() {
	}

	public BaseRequest(T request) {
		this.request = request;
	}

	public final T getRequest() {
		if (request == null){
			throw new SecurityException(RCode.REQUEST_ERROR.getCode(), RCode.REQUEST_ERROR.getMessage());
		}
		return request;
	}

	public final void setRequest(T request) {
		this.request = request;
	}

	public abstract String getRequestPath();

	public abstract boolean isPath(String path);

	public abstract String getMethod();

	public abstract String getHeader(String name);

	public abstract void setAttribute(String key, Object value);

	public abstract Object getAttribute(String key);

	public abstract <V> V getAttribute(String key, Class<V> tClass);

	public abstract List<String> getAttributeNames();

	public abstract void removeAttribute(String key);

	public final void removeAllAttribute() {
		final List<String> attributeNames = this.getAttributeNames();
		for (String attributeName : attributeNames) {
			this.removeAttribute(attributeName);
		}
	}

	public abstract byte[] getBodyBytes();
}
