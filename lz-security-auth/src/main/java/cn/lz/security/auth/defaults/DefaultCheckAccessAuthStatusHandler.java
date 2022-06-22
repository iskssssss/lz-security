package cn.lz.security.auth.defaults;

import cn.lz.security.LzCoreManager;
import cn.lz.security.auth.handler.ICheckAccessAuthStatusHandler;
import cn.lz.security.config.LzConfig;
import cn.lz.security.context.LzSecurityContextThreadLocal;
import cn.lz.security.context.model.BaseRequest;
import cn.lz.security.context.model.BaseResponse;
import cn.lz.tool.core.string.StringUtil;

/**
 * @Version 版权 Copyright(c)2021 LZ
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/16 16:50
 */
public class DefaultCheckAccessAuthStatusHandler implements ICheckAccessAuthStatusHandler {

	@Override
	public boolean check(
			BaseRequest<?> request,
			BaseResponse<?> response
	) {
		final BaseRequest servletRequest = LzSecurityContextThreadLocal.getServletRequest();
		final LzConfig lzConfig = LzCoreManager.getLzConfig();
		final String saveName = lzConfig.getTokenConfig().getName();
		final String authorizationToken = servletRequest.getHeader(saveName);
		return StringUtil.isNotEmpty(authorizationToken);
	}
}