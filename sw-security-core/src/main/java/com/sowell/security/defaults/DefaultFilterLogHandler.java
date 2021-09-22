package com.sowell.security.defaults;

import com.sowell.security.IcpManager;
import com.sowell.security.context.IcpContext;
import com.sowell.security.context.model.BaseRequest;
import com.sowell.security.context.model.BaseResponse;
import com.sowell.security.context.model.IcpStorage;
import com.sowell.security.log.BaseFilterLogHandler;
import com.sowell.security.log.IcpLoggerUtil;

/**
 * @Version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/16 15:00
 */
public class DefaultFilterLogHandler implements BaseFilterLogHandler {

	final IcpStorage<?> storage;

	public DefaultFilterLogHandler() {
		final IcpContext<?, ?> icpContext = IcpManager.getIcpContext();
		storage = icpContext.getStorage();
	}

	@Override
	public Object beforeHandler(
			BaseRequest<?> request,
			BaseResponse<?> response
	) {
		IcpLoggerUtil.info(getClass(), "客户端信息：" + storage.getUserAgentInfo().toJson());
		return null;
	}

	@Override
	public void afterHandler(
			BaseRequest<?> request,
			BaseResponse<?> response,
			Object logEntity,
			Exception ex
	) {
		IcpLoggerUtil.info(getClass(), "本次请求总时间：" + storage.getRequestTime().toString());
	}
}
