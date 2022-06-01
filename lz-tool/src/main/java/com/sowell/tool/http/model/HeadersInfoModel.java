package com.sowell.tool.http.model;

import java.net.HttpURLConnection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @Version 版权 Copyright(c)2021 LZ
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/10/18 17:11
 */
public class HeadersInfoModel extends HashMap<String, String> {

	public HeadersInfoModel(Map<? extends String, ? extends String> m) {
		super(m);
		init();
	}

	public HeadersInfoModel() {
		init();
	}

	private void init() {
		setAccept("*/*");
		setAcceptLanguage("zh-CN,zh;q=0.8");
		setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.131 Safari/537.36 Edg/92.0.902.73 lz");
	}

	public static HeadersInfoModel emptyMap() {
		return new HeadersInfoModel(Collections.emptyMap());
	}

	public void addConnectionRequestProperty(HttpURLConnection connection) {
		for (Entry<String, String> entry : this.entrySet()) {
			final String key = entry.getKey();
			final String value = entry.getValue();
			connection.setRequestProperty(key, value);
		}
	}

	public void addRequestProperty(String name, String value) {
		this.put(name, value);
	}

	public void addAllRequestProperty(Map<String, String> requestPropertyMap) {
		this.putAll(requestPropertyMap);
	}

	public void removeRequestProperty(String name) {
		this.remove(name);
	}

	public void setAccept(String accept) {
		this.removeAdd(HeadersTypeEnum.ACCEPT_KEY, accept);
	}

	public void setAcceptLanguage(String acceptLanguage) {
		this.removeAdd(HeadersTypeEnum.ACCEPT_LANGUAGE_KEY, acceptLanguage);
	}

	public void setUserAgent(String userAgent) {
		this.removeAdd(HeadersTypeEnum.USER_AGENT_KEY, userAgent);
	}

	public void setContentLength(String contentLength) {
		this.removeAdd(HeadersTypeEnum.CONTENT_LENGTH_KEY, contentLength);
	}

	public void setContentType(String contentType) {
		this.removeAdd(HeadersTypeEnum.CONTENT_TYPE_KEY, contentType);
	}

	public void setAuthorization(String authorization) {
		this.removeAdd(HeadersTypeEnum.AUTHORIZATION_KEY, authorization);
	}

	private void removeAdd(HeadersTypeEnum key, String value) {
		this.remove(key.type);
		this.put(key.type, value);
	}

	public enum HeadersTypeEnum {
		/**
		 * Content-Length
		 */
		CONTENT_LENGTH_KEY("Content-Length"),
		/**
		 * Content-Type
		 */
		CONTENT_TYPE_KEY("Content-Type"),
		/**
		 * Authorization
		 */
		AUTHORIZATION_KEY("Authorization"),
		/**
		 * Accept
		 */
		ACCEPT_KEY("Accept"),
		/**
		 * Accept-Language
		 */
		ACCEPT_LANGUAGE_KEY("Accept-Language"),
		/**
		 * User-Agent
		 */
		USER_AGENT_KEY("User-Agent");

		private final String type;

		HeadersTypeEnum(String type) {
			this.type = type;
		}

		public String getType() {
			return type;
		}
	}
}
