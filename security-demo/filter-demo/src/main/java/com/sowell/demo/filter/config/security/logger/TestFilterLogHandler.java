package com.sowell.demo.filter.config.security.logger;

import com.sowell.security.LzCoreManager;
import com.sowell.security.context.model.BaseRequest;
import com.sowell.security.context.model.BaseResponse;
import com.sowell.security.log.BaseFilterLogHandler;
import com.sowell.security.log.LzLoggerUtil;
import org.springframework.stereotype.Component;

/**
 * TODO
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @date 2021/10/27 16:42
 */
@Component
public class TestFilterLogHandler implements BaseFilterLogHandler {

	@Override
	public Object before(BaseRequest<?> request, BaseResponse<?> response) {
		LzLoggerUtil.info(getClass(), "自定义日志处理：beforeHandler(...)");
		return null;
	}

	@Override
	public void after(BaseRequest<?> request, BaseResponse<?> response, Object logEntity, Exception ex) {
		LzLoggerUtil.info(getClass(), "本次请求总时间：" + LzCoreManager.getStorage().getRequestTime().toString());
		LzLoggerUtil.info(getClass(), "自定义日志处理：afterHandler(...)");
	}
}
