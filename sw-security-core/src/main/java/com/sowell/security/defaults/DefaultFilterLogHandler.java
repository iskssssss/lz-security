package com.sowell.security.defaults;

import com.sowell.security.context.model.BaseRequest;
import com.sowell.security.log.BaseFilterLogHandler;

/**
 * @Version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/16 15:00
 */
public class DefaultFilterLogHandler implements BaseFilterLogHandler {

	@Override
	public Object beforeHandler(
			BaseRequest<?> request
	) {
		return "beforeHandler";
	}

	@Override
	public void afterHandler(
			Object logEntity,
			long requestTime,
			BaseRequest<?> request,
			int responseStatus,
			byte[] responseBytes,
			Exception ex
	) {
		System.out.println(logEntity);
	}
}
