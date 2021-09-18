package com.sowell.security.context.model;

import com.sowell.security.utils.BeanUtil;

import java.io.Closeable;

/**
 * @Version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/10 17:29
 */
public abstract class IcpStorage<T> implements Closeable {

	protected BaseRequest<T> request;

	protected IcpStorage(BaseRequest<T> request) {
		this.request = request;
	}

	void setAttribute(String key, Object value) {
		request.setAttribute(key, value);
	}

	<T> T getAttribute(String key, Class<T> tClass) {
		final Object attribute = request.getAttribute(key);
		if (attribute == null) {
			return null;
		}
		try {
			return (T) attribute;
		} catch (Exception e) {
			if (attribute instanceof CharSequence) {
				return BeanUtil.toBean(attribute.toString(), tClass);
			}
		}
		return null;
	}

	public Object getAttribute(String key) {
		return request.getAttribute(key);
	}
}
