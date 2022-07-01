package cn.lz.security.auth.defaults;

import cn.lz.security.LzCoreManager;
import cn.lz.security.auth.login.ICheckAccessAuthStatusHandler;
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
public class CheckAccessAuthStatusHandlerDefault implements ICheckAccessAuthStatusHandler {

	@Override
	public boolean check(
			BaseRequest<?> request,
			BaseResponse<?> response
	) {
		final BaseRequest servletRequest = LzSecurityContextThreadLocal.getRequest();
		final String saveName = LzCoreManager.getTokenConfig().getName();
		final String authorizationToken = servletRequest.getHeader(saveName);
		return StringUtil.isNotEmpty(authorizationToken);
	}
}
