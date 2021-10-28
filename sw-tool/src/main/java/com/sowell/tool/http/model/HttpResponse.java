package com.sowell.tool.http.model;

import com.sowell.tool.core.bytes.ByteUtil;
import com.sowell.tool.io.FastByteArrayOutputStream;
import com.sowell.tool.json.JsonUtil;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.nio.charset.Charset;

/**
 * @Author: sowell
 * @Date: 2021/08/21 18:33
 */
public class HttpResponse {

	/**
	 * http连接
	 */
	private final HttpURLConnection connection;
	/**
	 * 响应状态
	 */
	private int responseCode;
	/**
	 * 流
	 */
	private FastByteArrayOutputStream fastByteArrayOutputStream;
	/**
	 * 编码
	 */
	private final Charset charset;

	public HttpResponse(HttpURLConnection connection, Charset charset) {
		this.connection = connection;
		this.charset = charset;
	}

	public <T> T body(Class<T> tClass) {
		final String body = this.body();
		try {
			return JsonUtil.parseObject(body, tClass);
		} catch (Exception e) {
			return (T) body;
		}
	}

	public String body() {
		final byte[] bytes = bytes();
		return new String(bytes, this.charset);
	}

	public byte[] bytes() {
		try {
			if (this.fastByteArrayOutputStream != null && this.fastByteArrayOutputStream.size() > 0) {
				return fastByteArrayOutputStream.toByteArray();
			}
			readContent();
			if (null == this.fastByteArrayOutputStream) {
				return null;
			}
			return this.fastByteArrayOutputStream.toByteArray();
		} finally {
			disconnect();
		}
	}

	private void readContent() {
		try {
			this.responseCode = this.connection.getResponseCode();
			InputStream inputStream = this.connection.getErrorStream();
			if (inputStream == null) {
				inputStream = this.connection.getInputStream();
			}
			if (inputStream == null) {
				throw new IOException();
			}
			this.fastByteArrayOutputStream = ByteUtil.toFastByteArrayOutputStream(inputStream);
		} catch (IOException ioException) {
			throw new RuntimeException("获取响应内容失败！", ioException);
		}
	}

	private void disconnect() {
		try {
			if (this.connection == null) {
				return;
			}
			this.connection.disconnect();
		} catch (Throwable ignored) {

		}
	}

	public int getResponseCode() {
		return responseCode;
	}
}
