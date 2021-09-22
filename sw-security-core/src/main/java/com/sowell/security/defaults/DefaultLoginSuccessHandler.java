package com.sowell.security.defaults;

import com.sowell.security.auth.LoginSuccessHandler;
import com.sowell.security.context.model.BaseRequest;
import com.sowell.security.context.model.BaseResponse;
import com.sowell.security.enums.RCode;
import com.sowell.security.model.AuthDetails;
import com.sowell.security.utils.ServletUtil;

/**
 * @Version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/22 11:28
 */
public class DefaultLoginSuccessHandler implements LoginSuccessHandler {

	@Override
	public void success(
			BaseRequest<?> request,
			BaseResponse<?> response,
			AuthDetails<?> authDetails
	) {
		response.setStatus(200);
		ServletUtil.printResponse(response, RCode.SUCCESS);
	}
}
