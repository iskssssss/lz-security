package cn.lz.tool.http.model;

import cn.lz.tool.core.string.StringUtil;
import cn.lz.tool.http.enums.RequestMethodEnum;
import cn.lz.tool.http.enums.MediaType;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @Author: lz
 * @Date: 2021/08/19 20:16
 */
public class HttpRequest {
	/**
	 * 请求连接
	 */
	private final String requestUrl;
	/**
	 * http连接
	 */
	private HttpURLConnection connection;
	/**
	 * 请求头
	 */
	private final HeadersInfoModel headers;
	/**
	 * 请求类型
	 */
	private RequestMethodEnum method;
	/**
	 * 超时时间
	 */
	private Integer timeout = 10000;
	/**
	 * 编码
	 */
	private Charset charset = StandardCharsets.UTF_8;
	private boolean rest;

	/**
	 * 正文类型
	 */
	private String contentType;
	/**
	 * 表单数据
	 */
	private Map<String, Object> form;
	/**
	 * body数据
	 */
	private byte[] bodyByteList;


	public HttpRequest(String requestUrl) {
		this.requestUrl = requestUrl;
		this.form = new HashMap<>(4);
		this.headers = new HeadersInfoModel();
	}

	public HttpRequest method(RequestMethodEnum method) {
		this.method = method;
		return this;
	}

	public HttpRequest charset(Charset charset) {
		this.charset = charset;
		return this;
	}

	public HttpRequest timeout(Integer timeout) {
		this.timeout = timeout;
		return this;
	}

	public HttpRequest headers(HeadersInfoModel headers) {
		if (headers == null || headers.isEmpty()) {
			return this;
		}
		this.headers.addAllRequestProperty(headers);
		return this;
	}

	public HttpRequest contentType(String contentType) {
		this.contentType = contentType;
		this.headers.setContentType(contentType);
		return this;
	}

	public HttpRequest form(Map<String, Object> form) {
		this.form = new HashMap<>(4);
		if (this.contentType == null) {
			this.contentType = MediaType.APPLICATION_FORM_URLENCODED_VALUE;
		}
		this.bodyByteList = null;
		if (form == null) {
			return this;
			//throw new RuntimeException("表单内容不可为空！");
		}
		this.form.putAll(form);
		return this;
	}

	public HttpRequest body(String body) {
		if (StringUtil.isEmpty(body)) {
			throw new RuntimeException("body内容不可为空！");
		}
		if (this.contentType == null) {
			this.contentType = MediaType.APPLICATION_JSON_VALUE;
		}
		this.form = null;
		this.bodyByteList = body.getBytes(charset);
		final String contentLength = String.valueOf(this.bodyByteList.length);
		this.rest = true;
		this.headers.setContentLength(contentLength);
		return this;
	}

	public HttpResponse execute() {
		// 初始化请求
		initConnection();
		// 请求
		request();
		// 获取响应
		return new HttpResponse(this.connection, this.charset);
	}

	public void initConnection() {
		try {
			this.connection = getConnection();
			this.connection.setUseCaches(false);
			this.connection.setConnectTimeout(this.timeout);
			this.connection.setRequestMethod(this.method.toString());
			System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
			this.headers.addConnectionRequestProperty(this.connection);
		} catch (Exception ioException) {
			throw new RuntimeException("请求初始化错误。", ioException);
		}
	}

	public void request() {
		try {
			this.connection.setDoInput(true);

			if (this.method == RequestMethodEnum.GET && !this.rest) {
				this.connection.setDoOutput(false);
				this.connection.connect();
				return;
			}
			this.post();
		} catch (IOException ioException) {
			this.disconnect();
			if (ioException instanceof SocketTimeoutException || ioException instanceof ConnectException) {
				throw new RuntimeException("连接超时，请检查网络或请求地址(" + requestUrl + ")。");
			}
			throw new RuntimeException(ioException);
		}
	}

	private void post() throws IOException {
		byte[] bytes;
		if (this.bodyByteList == null) {
			Optional<String> requestStr = Optional.ofNullable(toParamsString());
			bytes = requestStr.orElse("").getBytes();
		} else {
			bytes = this.bodyByteList;
		}
		this.connection.setDoOutput(true);
		try (final OutputStream outputStream = this.connection.getOutputStream()) {
			outputStream.write(bytes);
			outputStream.flush();
		}
	}

	private String toParamsString() {
		try {
			if (this.form == null || this.form.isEmpty()) {
				return "";
			}
			StringBuilder params = new StringBuilder();
			for (Map.Entry<String, Object> entry : this.form.entrySet()) {
				final String key = entry.getKey();
				final Object value = entry.getValue();
				final String encodeKey = URLEncoder.encode(key, StandardCharsets.UTF_8.name());
				final String encodeValue = URLEncoder.encode((String) value, StandardCharsets.UTF_8.name());
				params.append("&").append(encodeKey).append("=").append(encodeValue);
			}
			return params.substring(1);
		} catch (UnsupportedEncodingException exception) {
			return null;
		}
	}

	private HttpURLConnection getConnection() {
		try {
			if (StringUtil.isEmpty(this.requestUrl)) {
				throw new RuntimeException("请求地址不可为空！");
			}
			URL url = new URL(this.requestUrl);
			return (HttpURLConnection) url.openConnection();
		} catch (MalformedURLException malformedURLException) {
			throw new RuntimeException("URL格式错误(" + this.requestUrl + ")！");
		} catch (IOException ioException) {
			throw new RuntimeException("URL初始化异常!", ioException);
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
}
