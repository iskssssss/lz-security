package com.sowell.security.auth.defaults;

import com.sowell.security.auth.login.LoginErrorHandler;
import com.sowell.security.context.model.BaseRequest;
import com.sowell.security.context.model.BaseResponse;
import com.sowell.tool.core.enums.RCode;
import com.sowell.security.exception.SecurityException;
import com.sowell.security.utils.ServletUtil;
import com.sowell.tool.http.enums.ContentTypeEnum;

/**
 * @Version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/22 11:29
 */
public class DefaultLoginErrorHandler implements LoginErrorHandler {

	@Override
	public void error(
			BaseRequest<?> request,
			BaseResponse<?> response,
			SecurityException securityException
	) {
		response.setStatus(403);
		ServletUtil.printResponse(response, ContentTypeEnum.JSON.name, RCode.REQUEST_ERROR);
	}
}
