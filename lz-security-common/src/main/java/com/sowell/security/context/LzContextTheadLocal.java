package com.sowell.security.context;

import com.sowell.security.context.model.BaseRequest;
import com.sowell.security.context.model.BaseResponse;
import com.sowell.security.context.model.LzStorage;

/**
 * @Version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/17 11:46
 */
public abstract class LzContextTheadLocal<RequestType, ResponseType> implements LzContext<RequestType, ResponseType> {

	@Override
	public final BaseRequest<RequestType> getRequest() {
		return LzSecurityContextThreadLocal.getServletRequest();
	}

	@Override
	public final BaseResponse<ResponseType> getResponse() {
		return LzSecurityContextThreadLocal.getServletResponse();
	}

	@Override
	public final LzStorage<RequestType> getStorage() {
		return LzSecurityContextThreadLocal.getLzStorage();
	}
}
