package com.sowell.security.context;

import com.sowell.security.context.model.BaseRequest;
import com.sowell.security.context.model.BaseResponse;
import com.sowell.security.context.model.IcpStorage;

/**
 * @Version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/17 11:46
 */
public abstract class IcpContextTheadLocal<RequestType, ResponseType> implements IcpContext<RequestType, ResponseType> {

	@Override
	public final BaseRequest<RequestType> getRequest() {
		return IcpSecurityContextThreadLocal.getServletRequest();
	}

	@Override
	public final BaseResponse<ResponseType> getResponse() {
		return IcpSecurityContextThreadLocal.getServletResponse();
	}

	@Override
	public final IcpStorage<RequestType> getStorage() {
		return IcpSecurityContextThreadLocal.getIcpStorage();
	}
}
