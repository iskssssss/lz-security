package cn.lz.security.context;

import cn.lz.security.context.model.BaseRequest;
import cn.lz.security.context.model.BaseResponse;
import cn.lz.security.context.model.Storage;
import cn.lz.tool.reflect.ReflectUtil;

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
	default BaseRequest<RequestType> getRequest() {
		return (BaseRequest<RequestType>) LzSecurityContextThreadLocal.getRequest();
	}

	/**
	 * 获取 BaseResponse
	 *
	 * @return BaseResponse
	 */
	default BaseResponse<ResponseType> getResponse() {
		return (BaseResponse<ResponseType>) LzSecurityContextThreadLocal.getResponse();
	}

	/**
	 * 获取 LzStorage
	 *
	 * @return LzStorage
	 */
	default Storage<RequestType> getStorage() {
		return (Storage<RequestType>) LzSecurityContextThreadLocal.getLzStorage();
	}

	/**
	 * 路径匹配
	 *
	 * @param pattern
	 * @param path
	 * @return
	 */
	boolean matchUrl(String pattern, String path);

	/**
	 * 创建对象
	 *
	 * @param tClass 创建对象类
	 * @param <T>    创建对象类型
	 * @return 创建对象
	 */
	default <T> T createBean(Class<T> tClass) {
		return ReflectUtil.newInstance(tClass);
	}
}
