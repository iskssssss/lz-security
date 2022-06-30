package cn.lz.security.tool.mode;

import cn.hutool.core.io.FastByteArrayOutputStream;
import cn.lz.security.LzCoreManager;
import cn.lz.security.context.LzContext;
import cn.lz.security.context.model.BaseRequest;
import cn.lz.security.exception.base.SecurityException;
import cn.lz.tool.core.enums.RCode;
import cn.lz.tool.core.string.StringUtil;
import cn.lz.tool.io.IoUtil;

import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

/**
 * @Version 版权 Copyright(c)2021 LZ
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
		final Cookie[] cookies = request.getCookies();
		if (cookies == null || cookies.length < 1) {
			return null;
		}
		for (Cookie cookie : cookies) {
			final String cookieName = cookie.getName();
			if (StringUtil.isEmpty(cookieName)) {
				continue;
			}
			if (cookieName.startsWith(name)) {
				return cookie.getValue();
			}
		}
		return null;
	}

	@Override
	public String getRemoteAddr() {
		return getRequest().getRemoteAddr();
	}
}
