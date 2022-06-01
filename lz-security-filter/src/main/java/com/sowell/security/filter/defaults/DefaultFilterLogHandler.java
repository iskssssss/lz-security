package com.sowell.security.filter.defaults;

import com.sowell.security.IcpCoreManager;
import com.sowell.security.context.model.BaseRequest;
import com.sowell.security.context.model.BaseResponse;
import com.sowell.security.log.BaseFilterLogHandler;
import com.sowell.security.log.IcpLoggerUtil;

/**
 * 默认过滤日志记录处理器
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @date 2021/09/16 15:00
 */
public class DefaultFilterLogHandler implements BaseFilterLogHandler {

	@Override
	public Object before(
			BaseRequest<?> request,
			BaseResponse<?> response
	) {
		IcpLoggerUtil.info(getClass(), "客户端信息：" + IcpCoreManager.getStorage().getUserAgentInfo().toJson());
		return null;
	}

	@Override
	public void after(
			BaseRequest<?> request,
			BaseResponse<?> response,
			Object logEntity,
			Exception ex
	) {
		IcpLoggerUtil.info(getClass(), "本次请求总时间：" + IcpCoreManager.getStorage().getRequestTime().toString());
	}
}
