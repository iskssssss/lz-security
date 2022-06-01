package com.sowell.security.tool.mode;

import cn.hutool.core.io.FastByteArrayOutputStream;
import com.sowell.security.LzCoreManager;
import com.sowell.security.context.LzContext;
import com.sowell.security.context.model.BaseRequest;
import com.sowell.security.exception.base.SecurityException;
import com.sowell.security.tool.utils.CookieUtil;
import com.sowell.tool.core.enums.RCode;
import com.sowell.tool.io.IoUtil;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
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
public class LzRequest extends BaseRequest<HttpServletRequest> {

	public LzRequest(HttpServletRequest request) {
		super(request);
	}

	public LzRequest(HttpServletRequest request, boolean decrypt) {
		super(request);
		super.decrypt = decrypt;
	}

	@Override
	public String getRequestURL() {
		return getRequest().getRequestURL().toString();
	}

	@Override
	public String getRequestPath() {
		return getRequest().getServletPath();
	}

	@Override
	public boolean matchUrl(String path) {
		final LzContext<?, ?> lzContext = LzCoreManager.getLzContext();
		return lzContext.matchUrl(path, this.getRequestPath());
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
	public String getCookieValue(String name) {
		return CookieUtil.getValue(getRequest(), name);
	}

	@Override
	public String getRemoteAddr() {
		return getRequest().getRemoteAddr();
	}
}
