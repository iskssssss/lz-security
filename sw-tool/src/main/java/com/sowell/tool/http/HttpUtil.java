package com.sowell.tool.http;

import com.sowell.tool.http.model.HttpRequest;
import com.sowell.tool.http.enums.ContentTypeEnum;
import com.sowell.tool.http.enums.RequestMethodEnum;
import com.sowell.tool.http.model.HeadersInfoModel;
import com.sowell.tool.json.JsonUtil;

import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @Author: sowell
 * @Date: 2021/08/19 18:08
 */
@SuppressWarnings("unchecked")
public class HttpUtil {
	// base 请求
	public static HttpRequest baseHttpRequest(String requestUrl, HeadersInfoModel headersInfoModel, RequestMethodEnum requestMethodEnum) {
		return baseHttpRequest(requestUrl, headersInfoModel, requestMethodEnum, 5000);
	}
	public static HttpRequest baseHttpRequest(String requestUrl, HeadersInfoModel headersInfoModel, RequestMethodEnum requestMethodEnum, int timeout) {
		return new HttpRequest(requestUrl)
				.method(requestMethodEnum)
				.headers(headersInfoModel)
				.timeout(timeout)
				.charset(StandardCharsets.UTF_8);
	}

	// delete 相关请求
	public static HttpRequest deleteReHttpRequest(String requestUrl, HeadersInfoModel headersInfoModel) {
		return baseHttpRequest(requestUrl, headersInfoModel, RequestMethodEnum.DELETE);
	}
	public static String deleteReString(String requestUrl) {
		return deleteReString(requestUrl, null);
	}
	public static String deleteReString(String requestUrl, HeadersInfoModel headersInfoModel) {
		final HttpRequest httpRequest = deleteReHttpRequest(requestUrl, headersInfoModel);
		return httpRequest.execute().body();
	}
	public static <T> T delete(String requestUrl, Class<T> tClass) {
		return delete(requestUrl, null, tClass);
	}
	public static <T> T delete(String requestUrl, HeadersInfoModel headersInfoModel, Class<T> tClass) {
		return deleteReHttpRequest(requestUrl, headersInfoModel).execute().body(tClass);
	}

	// get 相关请求
	public static HttpRequest getReHttpRequest(String requestUrl) {
		return getReHttpRequest(requestUrl, null);
	}
	public static HttpRequest getReHttpRequest(String requestUrl, HeadersInfoModel headersInfoModel) {
		return baseHttpRequest(requestUrl, headersInfoModel, RequestMethodEnum.GET);
	}
	public static String getReString(String requestUrl) {
		return getReString(requestUrl, null);
	}
	public static String getReString(String requestUrl, HeadersInfoModel headersInfoModel) {
		final HttpRequest httpRequest = getReHttpRequest(requestUrl, headersInfoModel);
		return httpRequest.execute().body();
	}
	public static <T> T get(String requestUrl, Class<T> tClass) {
		return get(requestUrl, null, tClass);
	}
	public static <T> T get(String requestUrl, HeadersInfoModel headersInfoModel, Class<T> tClass) {
		return getReHttpRequest(requestUrl, headersInfoModel).execute().body(tClass);
	}

	// post 相关请求
	public static HttpRequest postReHttpRequest(String requestUrl, HeadersInfoModel headersInfoModel, ContentTypeEnum contentTypeEnum, Object data) {
		final HttpRequest httpRequest = new HttpRequest(requestUrl)
				.headers(headersInfoModel).timeout(6000)
				.contentType(contentTypeEnum)
				.method(RequestMethodEnum.POST)
				.charset(StandardCharsets.UTF_8);
		if (data != null) {
			if (contentTypeEnum == ContentTypeEnum.JSON) {
				httpRequest.body(JsonUtil.toJsonString(data));
			} else {
				if (data instanceof Map) {
					httpRequest.form(((Map) data));
				}
			}
		}
		return httpRequest;
	}
	// json
	public static HttpRequest postForJsonReHttpRequest(String requestUrl, HeadersInfoModel headersInfoModel, Object body) {
		return postReHttpRequest(requestUrl, headersInfoModel, ContentTypeEnum.JSON, body);
	}
	public static String postForJsonReString(String requestUrl, Object body) {
		return postForJsonReString(requestUrl, null, body);
	}
	public static String postForJsonReString(String requestUrl, HeadersInfoModel headersInfoModel, Object body) {
		return postForJson(requestUrl, headersInfoModel, body, String.class);
	}
	public static <T> T postForJson(String requestUrl, Object body, Class<T> tClass) {
		return postForJson(requestUrl, null, body, tClass);
	}
	public static <T> T postForJson(String requestUrl, HeadersInfoModel headersInfoModel, Object body, Class<T> tClass) {
		final HttpRequest httpRequest = postForJsonReHttpRequest(requestUrl, headersInfoModel, body);
		return httpRequest.execute().body(tClass);
	}
	// form
	public static HttpRequest postForFormReHttpRequest(String requestUrl, HeadersInfoModel headersInfoModel, Map<String, Object> form) {
		return postReHttpRequest(requestUrl, headersInfoModel, ContentTypeEnum.FORM, form);
	}
	public static String postForFormReString(String requestUrl, Map<String, Object> form) {
		return postForFormReString(requestUrl, null, form);
	}
	public static String postForFormReString(String requestUrl, HeadersInfoModel headersInfoModel, Map<String, Object> form) {
		return postForForm(requestUrl, headersInfoModel, form, String.class);
	}
	public static <T> T postForForm(String requestUrl, Map<String, Object> form, Class<T> tClass) {
		return postForForm(requestUrl, null, form, tClass);
	}
	public static <T> T postForForm(String requestUrl, HeadersInfoModel headersInfoModel, Map<String, Object> form, Class<T> tClass) {
		final HttpRequest httpRequest = postForFormReHttpRequest(requestUrl, headersInfoModel, form);
		return httpRequest.execute().body(tClass);
	}
}
