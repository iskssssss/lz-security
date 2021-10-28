package com.sowell.security.auth;

import com.sowell.security.context.model.BaseRequest;
import com.sowell.security.context.model.BaseResponse;

/**
 * @Version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/16 16:22
 */
public interface ICheckAccessAuthStatusHandler {

	/**
	 * 校验认证状态
	 *
	 * @param request  请求
	 * @param response 响应
	 * @return 认证状态
	 */
	boolean check(
			BaseRequest<?> request,
			BaseResponse<?> response
	);

}
