package com.sowell.security.context;

import com.sowell.security.mode.SwRequest;
import com.sowell.security.model.UserAgentInfo;
import com.sowell.security.utils.StringUtil;
import com.sowell.security.utils.UserAgentUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @Version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/10 17:37
 */
public class IcpStorage extends com.sowell.security.context.model.IcpStorage<HttpServletRequest> {

	private String requestUrl;

	private UserAgentInfo userAgentInfo = null;

	public IcpStorage(
			HttpServletRequest request
	) {
		super(new SwRequest(request));
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
					//String clientIp = ServletUtil.getClientIP(super.request);
					//userAgentInfo.setIpAddr(clientIp);
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
		this.requestUrl = null;
		this.userAgentInfo = null;
	}
}
