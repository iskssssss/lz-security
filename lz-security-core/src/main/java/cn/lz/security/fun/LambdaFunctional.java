package cn.lz.security.fun;

/**
 * TODO
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 LZ
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
