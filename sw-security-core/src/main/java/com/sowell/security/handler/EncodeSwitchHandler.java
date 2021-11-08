package com.sowell.security.handler;

import com.sowell.security.context.model.BaseRequest;

/**
 * 加解密开关处理器
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @date 2021/11/08 09:00
 */
public interface EncodeSwitchHandler {

	/**
	 * 请求数据是否解密
	 *
	 * @param request 响应流
	 * @return 是否解密
	 */
	boolean decrypt(BaseRequest<?> request);

	/**
	 * 响应数据是否加密
	 *
	 * @param request 响应流
	 * @return 是否加密
	 */
	boolean encrypt(BaseRequest<?> request);
}
