package cn.lz.security.filter.mode;

import cn.lz.security.filter.wrapper.HttpServletResponseWrapper;
import cn.lz.security.context.model.BaseResponse;
import cn.lz.security.exception.base.SecurityException;
import cn.lz.tool.core.bytes.ByteUtil;
import cn.lz.tool.core.enums.RCode;
import cn.lz.tool.core.string.StringUtil;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Version 版权 Copyright(c)2021 LZ
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/17 11:41
 */
public class LzResponse extends BaseResponse<HttpServletResponse> {

	public LzResponse(HttpServletResponse response) {
		super(response);
	}

	public LzResponse(HttpServletResponse response, boolean encrypt) {
		super(response);
		super.encrypt = encrypt;
	}

	@Override
	public BaseResponse<HttpServletResponse> setHeader(String name, String value) {
		getResponse().setHeader(name, value);
		return this;
	}

	@Override
	public BaseResponse<HttpServletResponse> setHeader(String name, Collection<String> valueList) {
		if (valueList == null || valueList.isEmpty()) {
			return this;
		}
		HttpServletResponse response = getResponse();
		String header = response.getHeader(name);
		if (StringUtil.isEmpty(header)) {
			response.addHeader(name, String.join(",", new HashSet<>(valueList)));
			return this;
		}
		String[] split = header.split(",");
		Set<String> valueSet = Arrays.stream(split).filter(StringUtil::isNotEmpty).collect(Collectors.toSet());
		valueSet.addAll(valueList);
		response.addHeader(name, String.join(",", valueSet));
		return this;
	}

	@Override
	public BaseResponse<HttpServletResponse> addHeader(String name, String value) {
		getResponse().addHeader(name, value);
		return this;
	}

	@Override
	public void print(String message) {
		final byte[] bytes = ByteUtil.toBytes(message);
		if (StringUtil.isEmpty(bytes)) {
			return;
		}
		this.print(bytes);
	}

	@Override
	public void print(byte[] bytes) {
		this.print(bytes, 0, bytes.length);
	}

	@Override
	public void print(byte[] bytes, int off, int len) {
		if (StringUtil.isEmpty(bytes)) {
			return;
		}
		final int length = bytes.length;
		if (off < 0 || off > length || len < 0 || len > length) {
			return;
		}
		final HttpServletResponse response = getResponse();
		try (ServletOutputStream outputStream = response instanceof HttpServletResponseWrapper ?
				((HttpServletResponseWrapper) response).getResponseOutputStream() : response.getOutputStream()) {
			response.setContentLength(len);
			outputStream.write(bytes, off, len);
			outputStream.flush();
		} catch (IOException ioException) {
			throw new SecurityException(RCode.UNKNOWN_MISTAKE.getCode(), RCode.UNKNOWN_MISTAKE.getMessage(), ioException);
		}
	}

	@Override
	public byte[] getResponseDataBytes() {
		if (getResponse() instanceof HttpServletResponseWrapper) {
			final HttpServletResponseWrapper responseWrapper = (HttpServletResponseWrapper) getResponse();
			try {
				return responseWrapper.toByteArray();
			} catch (IOException ioException) {
				throw new RuntimeException("数据获取异常", ioException);
			}
		}
		final ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		try (PrintWriter ignored = new PrintWriter(new OutputStreamWriter(bytes, StandardCharsets.UTF_8))) {
			bytes.flush();
		} catch (IOException ioException) {
			return null;
		}
		return bytes.toByteArray();
	}

	@Override
	public int getStatus() {
		return getResponse().getStatus();
	}

	@Override
	public void setStatus(int status) {
		getResponse().setStatus(status);
	}

	@Override
	public void addCookie(String name, String value, String path, String domain, int expiry) {
		this.setCookie(getResponse(), name, value, path, domain, expiry);
	}

	@Override
	public void removeCookie(String name) {
		this.setCookie(response, name, null, null, null, 0);
	}

	/**
	 * 设置Cookie
	 */
	private void setCookie(HttpServletResponse response, String name, String value, String path, String domain, int expiry) {
		Cookie cookie = new Cookie(name, value);
		cookie.setPath(StringUtil.isEmpty(path) ? "/" : path);
		if (StringUtil.isNotEmpty(domain)) {
			cookie.setDomain(domain);
		}
		cookie.setHttpOnly(false);
		cookie.setMaxAge(expiry);
		response.addCookie(cookie);
	}
}
