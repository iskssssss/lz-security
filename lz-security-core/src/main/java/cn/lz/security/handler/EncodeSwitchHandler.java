package cn.lz.security.handler;

import cn.lz.security.context.model.BaseRequest;

/**
 * 加解密开关处理器
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 LZ
 * @date 2021/11/08 09:00
 */
public interface EncodeSwitchHandler {

	/**
	 * 请求数据是否解密
	 *
	 * @param request 响应流
	 * @return 是否解密
	 */
	boolean isDecrypt(BaseRequest<?> request);

	/**
	 * 响应数据是否加密
	 *
	 * @param request 响应流
	 * @return 是否加密
	 */
	boolean isEncrypt(BaseRequest<?> request);

	/**
	 * 获取响应数据类型字符串
	 *
	 * @return 响应数据类型字符串
	 */
	String responseContentType(BaseRequest<?> request);

	/**
	 * 获取请求数据类型字符串
	 *
	 * @return 请求数据类型字符串
	 */
	String requestContentType(BaseRequest<?> request);
}
