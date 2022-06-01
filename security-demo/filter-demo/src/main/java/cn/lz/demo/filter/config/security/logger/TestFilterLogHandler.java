package cn.lz.demo.filter.config.security.logger;

import cn.lz.security.LzCoreManager;
import cn.lz.security.context.model.BaseRequest;
import cn.lz.security.context.model.BaseResponse;
import cn.lz.security.log.BaseFilterLogHandler;
import cn.lz.security.log.LzLoggerUtil;
import org.springframework.stereotype.Component;

/**
 * TODO
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 LZ
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
