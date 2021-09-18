package com.sowell.security.mode;

import cn.hutool.core.io.FastByteArrayOutputStream;
import com.sowell.security.IcpManager;
import com.sowell.security.context.IcpContext;
import com.sowell.security.context.model.BaseRequest;
import com.sowell.security.enums.RCode;
import com.sowell.security.exception.SecurityException;
import com.sowell.security.utils.BeanUtil;
import com.sowell.security.utils.IoUtil;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

/**
 * @Version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/17 11:41
 */
public class SwRequest extends BaseRequest<HttpServletRequest> {

	public SwRequest(HttpServletRequest request) {
		super(request);
	}

	@Override
	public String getRequestPath() {
		return getRequest().getServletPath();
	}

	@Override
	public boolean isPath(String path) {
		final IcpContext icpContext = IcpManager.getIcpContext();
		return icpContext.matchUrl(path, this.getRequestPath());
	}

	@Override
	public String getMethod() {
		return getRequest().getMethod();
	}

	@Override
	public void setAttribute(String key, Object value) {
		getRequest().setAttribute(key, value);
	}

	@Override
	public Object getAttribute(String key) {
		return getRequest().getAttribute(key);
	}

	@Override
	public <V> V getAttribute(String key, Class<V> tClass) {
		final Object attribute = getAttribute(key);
		if (attribute == null) {
			return null;
		}
		try {
			return (V) attribute;
		} catch (Exception e) {
			if (attribute instanceof CharSequence) {
				return BeanUtil.toBean(attribute.toString(), tClass);
			}
		}
		return null;
	}

	@Override
	public List<String> getAttributeNames() {
		List<String> nameList = new LinkedList<>();
		for (Enumeration<String> e = getRequest().getAttributeNames(); e.hasMoreElements(); ) {
			final String name = e.nextElement();
			nameList.add(name);
		}
		return nameList;
	}

	@Override
	public void removeAttribute(String key) {
		getRequest().removeAttribute(key);
	}

	@Override
	public String getHeader(String name) {
		return getRequest().getHeader(name);
	}

	@Override
	public byte[] getBodyBytes() {
		try (ServletInputStream inputStream = getRequest().getInputStream()) {
			final FastByteArrayOutputStream byteArrayOutputStream = IoUtil.read(inputStream);
			return byteArrayOutputStream.toByteArray();
		} catch (IOException ioException) {
			throw new SecurityException(RCode.UNKNOWN_MISTAKE.getCode(), RCode.UNKNOWN_MISTAKE.getMessage(), ioException);
		}
	}

	@Override
	public String getRemoteAddr() {
		return getRequest().getRemoteAddr();
	}

	public Method getControllerMethod() {
		return IcpManager.getMethodByInterfaceUrl(getRequestPath());
	}
}
