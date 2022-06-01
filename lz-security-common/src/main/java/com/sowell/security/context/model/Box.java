package com.sowell.security.context.model;

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

	private BaseRequest<?> request;
	private BaseResponse<?> response;

	private IcpStorage<?> icpStorage;

	public Box() {
	}

	public Box(
			BaseRequest<?> request,
			BaseResponse<?> response,
			IcpStorage<?> icpStorage
	) {
		this.request = request;
		this.response = response;
		this.icpStorage = icpStorage;
	}

	public BaseRequest<?> getRequest() {
		return request;
	}

	public BaseResponse<?> getResponse() {
		return response;
	}

	public IcpStorage<?> getIcpStorage() {
		return icpStorage;
	}

	@Override
	public void close() throws IOException {
		this.request = null;
		this.response = null;
		this.icpStorage.close();
	}
}
