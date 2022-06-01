package cn.lz.security.context;

import cn.lz.security.context.model.BaseRequest;
import cn.lz.security.context.model.BaseResponse;
import cn.lz.security.context.model.LzStorage;

/**
 * @Version 版权 Copyright(c)2021 LZ
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
