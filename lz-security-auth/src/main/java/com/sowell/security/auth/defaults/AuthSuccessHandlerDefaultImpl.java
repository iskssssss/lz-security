package com.sowell.security.auth.defaults;

import com.sowell.security.auth.login.AuthSuccessHandler;
import com.sowell.security.context.model.BaseRequest;
import com.sowell.security.context.model.BaseResponse;
import com.sowell.tool.core.enums.RCode;
import com.sowell.tool.http.enums.ContentTypeEnum;
import com.sowell.tool.jwt.model.AuthDetails;
import com.sowell.security.utils.ServletUtil;

/**
 * 认证成功处理器 默认实现
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @date 2021/6/23 17:21
 */
public class AuthSuccessHandlerDefaultImpl implements AuthSuccessHandler {

	@Override
	public void success(BaseRequest<?> request, BaseResponse<?> response, AuthDetails<?> authDetails) {
		response.setStatus(200);
		ServletUtil.printResponse(response, ContentTypeEnum.JSON.name, RCode.SUCCESS);
	}
}
