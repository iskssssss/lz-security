package com.sowell.security.context.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Closeable;

/**
 * @Version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/10 17:29
 */
public abstract class IcpAbstractStorage implements Closeable {

	protected HttpServletRequest request;

	protected HttpServletResponse response;

	private boolean isSaveRequestLog = false;

	public HttpServletRequest getRequest() {
		return request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public void setSaveRequestLog(boolean saveRequestLog) {
		isSaveRequestLog = saveRequestLog;
	}

	public boolean getSaveRequestLog() {
		return isSaveRequestLog;
	}

	public void setAttribute(String key, Object value){
		final HttpServletRequest request = getRequest();
		request.setAttribute(key, value);
	}

	public Object getAttribute(String key){
		final HttpServletRequest request = getRequest();
		return request.getAttribute(key);
	}
}
