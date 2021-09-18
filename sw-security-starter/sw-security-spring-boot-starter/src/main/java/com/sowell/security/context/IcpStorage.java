package com.sowell.security.context;

import com.sowell.security.mode.SwRequest;

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

	public IcpStorage(
			SwRequest request
	) {
		super(request);
	}

	@Override
	public void close() throws IOException {
		super.request = null;
		super.startRequestTime = null;
		super.userAgentInfo = null;
	}
}
