package com.sowell.security.filter;

/**
 * @Version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/12 22:41
 */
public interface IcpFilterAuthStrategy {

	/**
	 * 执行方法
	 *
	 * @param params 执行参数
	 */
	void run(Object... params);
}
