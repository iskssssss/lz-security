package cn.lz.security.filter.defaults;

import cn.lz.security.context.model.BaseRequest;
import cn.lz.security.context.model.BaseResponse;
import cn.lz.security.exception.base.SecurityException;
import cn.lz.security.handler.BaseFilterErrorHandler;
import cn.lz.tool.core.enums.RCode;
import cn.lz.tool.core.model.RequestResult;
import cn.lz.tool.json.JsonUtil;

/**
 * 默认过滤错误处理器
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 LZ
 * @date 2021/09/16 14:51
 */
public class DefaultFilterErrorHandler implements BaseFilterErrorHandler<RequestResult> {

	@Override
	public RequestResult errorHandler(
			BaseRequest<?> request, BaseResponse<?> response, SecurityException securityException
	) {
		final Object data = securityException.getResponseData();
		RequestResult requestResult = new RequestResult();
		requestResult.setCode(securityException.getResponseCode());
		requestResult.setData(data == null ? null : JsonUtil.toJsonString(data));
		requestResult.setMessage(securityException.getMessage());

		if (securityException.getResponseCode().equals(RCode.INTERNAL_SERVER_ERROR.getCode())) {
			response.setStatus(requestResult.getCode());
		}
		return requestResult;
	}
}
