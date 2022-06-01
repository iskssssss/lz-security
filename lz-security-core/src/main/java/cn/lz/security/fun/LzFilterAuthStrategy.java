package cn.lz.security.fun;

/**
 * @Version 版权 Copyright(c)2021 LZ
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/12 22:41
 */
public interface LzFilterAuthStrategy {

	/**
	 * 执行方法
	 *
	 * @param params 执行参数
	 */
	void run(Object... params);
}
