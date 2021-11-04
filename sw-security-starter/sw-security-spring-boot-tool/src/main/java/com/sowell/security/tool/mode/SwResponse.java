package com.sowell.security.tool.mode;

import com.sowell.security.context.model.BaseResponse;
import com.sowell.security.exception.base.SecurityException;
import com.sowell.security.tool.utils.CookieUtil;
import com.sowell.security.tool.wrapper.HttpServletResponseWrapper;
import com.sowell.tool.core.bytes.ByteUtil;
import com.sowell.tool.core.enums.RCode;
import com.sowell.tool.core.string.StringUtil;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

/**
 * @Version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/17 11:41
 */
public class SwResponse extends BaseResponse<HttpServletResponse> {

	public SwResponse(HttpServletResponse response) {
		super(response);
	}

	@Override
	public BaseResponse<HttpServletResponse> setHeader(String name, String value) {
		getResponse().setHeader(name, value);
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
		CookieUtil.set(getResponse(), name, value, path, domain, expiry);
	}

	@Override
	public void removeCookie(String name) {
		CookieUtil.remove(getResponse(), name);
	}
}
