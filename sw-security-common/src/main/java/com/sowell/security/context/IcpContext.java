package com.sowell.security.context;

import com.sowell.security.config.IcpConfig;
import com.sowell.security.context.model.BaseRequest;
import com.sowell.security.context.model.BaseResponse;
import com.sowell.security.context.model.IcpStorage;

/**
 * @Version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/10 16:29
 */
public interface IcpContext<RequestType, ResponseType> {

	/**
	 * 获取 BaseRequest
	 *
	 * @return BaseRequest
	 */
	BaseRequest<RequestType> getRequest();

	/**
	 * 获取 BaseResponse
	 *
	 * @return BaseResponse
	 */
	BaseResponse<ResponseType> getResponse();

	/**
	 * 获取 IcpStorage
	 *
	 * @return IcpStorage
	 */
	IcpStorage<RequestType> getStorage();

	/**
	 * 路径匹配
	 *
	 * @param pattern
	 * @param path
	 * @return
	 */
	boolean matchUrl(String pattern, String path);

	default void setAttribute(String key, Object value) {
		getRequest().setAttribute(key, value);
	}

	default Object setAttribute(String key) {
		return getRequest().getAttribute(key);
	}
}
