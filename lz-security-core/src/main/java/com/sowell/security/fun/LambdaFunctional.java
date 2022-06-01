package com.sowell.security.fun;

/**
 * TODO
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @date 2021/10/28 16:10
 */
@FunctionalInterface
public interface LambdaFunctional<T> {

	/**
	 * 执行
	 *
	 * @param params 参数列表
	 * @return 结果
	 */
	T run(Object... params);
}
