package com.sowell.security.context;

import com.sowell.security.model.UserAgentInfo;
import com.sowell.security.utils.ServletUtil;
import com.sowell.security.utils.StringUtil;
import com.sowell.security.utils.UserAgentUtil;
import com.sowell.security.context.model.IcpAbstractStorage;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/10 17:37
 */
public class IcpStorage extends IcpAbstractStorage {

	private String requestUrl;

	private UserAgentInfo userAgentInfo = null;

	public IcpStorage(
			ServletRequest request,
			ServletResponse response
	) {
		super.request = (HttpServletRequest) request;
		super.response = (HttpServletResponse) response;
		this.requestUrl = ServletUtil.getLookupPathForRequest(super.request);
	}

	public UserAgentInfo getUserAgentInfo() {
		if (this.userAgentInfo == null) {
			synchronized (IcpStorage.class) {
				if (this.userAgentInfo == null) {
					String ua = super.request.getHeader("User-Agent");
					if (StringUtil.isEmpty(ua)) {
						return null;
					}
					this.userAgentInfo = UserAgentUtil.getUserAgentInfo(ua);
					String clientIp = ServletUtil.getClientIP(super.request);
					userAgentInfo.setIpAddr(clientIp);
				}
			}
		}
		return this.userAgentInfo;
	}

	public String getRequestUrl() {
		return this.requestUrl;
	}

	@Override
	public void close() throws IOException {
		super.request = null;
		super.response = null;
		this.requestUrl = null;
		this.userAgentInfo = null;
	}
}
