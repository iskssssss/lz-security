package com.sowell.security.fun;

/**
 * @Version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/17 14:00
 */
@FunctionalInterface
public interface IcpFilterFunction<RequestType, ResponseType> {

	/**
	 * 执行的方法
	 */
	void run(RequestType request, ResponseType response);
}
