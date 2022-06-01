package cn.lz.security.filter.defaults;

import cn.lz.security.LzCoreManager;
import cn.lz.security.context.model.BaseRequest;
import cn.lz.security.context.model.BaseResponse;
import cn.lz.security.log.BaseFilterLogHandler;
import cn.lz.security.log.LzLoggerUtil;

/**
 * 默认过滤日志记录处理器
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 LZ
 * @date 2021/09/16 15:00
 */
public class DefaultFilterLogHandler implements BaseFilterLogHandler {

	@Override
	public Object before(
			BaseRequest<?> request,
			BaseResponse<?> response
	) {
		LzLoggerUtil.info(getClass(), "客户端信息：" + LzCoreManager.getStorage().getUserAgentInfo().toJson());
		return null;
	}

	@Override
	public void after(
			BaseRequest<?> request,
			BaseResponse<?> response,
			Object logEntity,
			Exception ex
	) {
		LzLoggerUtil.info(getClass(), "本次请求总时间：" + LzCoreManager.getStorage().getRequestTime().toString());
	}
}
