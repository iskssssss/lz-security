package com.sowell.security.tool.context;

import com.sowell.security.context.model.IcpStorage;
import com.sowell.security.tool.mode.SwRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @Version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/10 17:37
 */
public class IcpSpringStorage extends IcpStorage<HttpServletRequest> {

	public IcpSpringStorage(
			SwRequest request,
			long startRequestTime
	) {
		super(request, startRequestTime);
	}

	@Override
	public void close() throws IOException {
		super.request = null;
		super.userAgentInfo = null;
	}
}
