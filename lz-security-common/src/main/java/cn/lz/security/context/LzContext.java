package cn.lz.security.context;

import cn.lz.security.context.model.BaseRequest;
import cn.lz.security.context.model.BaseResponse;
import cn.lz.security.context.model.Storage;

/**
 * 上下文
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 LZ
 * @date 2021/09/10 16:29
 */
public interface LzContext<RequestType, ResponseType> {

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
	 * 获取 LzStorage
	 *
	 * @return LzStorage
	 */
	Storage<RequestType> getStorage();

	/**
	 * 路径匹配
	 *
	 * @param pattern
	 * @param path
	 * @return
	 */
	boolean matchUrl(String pattern, String path);
}
