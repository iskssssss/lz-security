package com.sowell.security.context.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Closeable;
import java.io.IOException;

/**
 * @Version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/10 17:29
 */
public class Box implements Closeable {

	private HttpServletRequest request;

	private HttpServletResponse response;

	private IcpAbstractStorage icpAbstractStorage;

	public Box() {
	}

	public Box(HttpServletRequest request, HttpServletResponse response, IcpAbstractStorage icpAbstractStorage) {
		this.request = request;
		this.response = response;
		this.icpAbstractStorage = icpAbstractStorage;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public IcpAbstractStorage getIcpAbstractStorage() {
		return icpAbstractStorage;
	}

	@Override
	public void close() throws IOException {
		this.request = null;
		this.response = null;
		this.icpAbstractStorage.close();
	}
}
