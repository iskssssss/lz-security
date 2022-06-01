package cn.lz.security.fun;

/**
 * @Version 版权 Copyright(c)2021 LZ
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/17 14:00
 */
@FunctionalInterface
public interface LzFilterFunction<RequestType, ResponseType> {

	/**
	 * 执行的方法
	 */
	void run(RequestType request, ResponseType response);
}
