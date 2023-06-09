package cn.lz.security.filter.filters;

import cn.lz.security.context.LzContext;
import cn.lz.security.context.model.BaseRequest;
import cn.lz.security.context.model.BaseResponse;
import cn.lz.security.exception.base.SecurityException;
import cn.lz.security.filter.context.SpringBootContextTheadLocal;
import cn.lz.security.filter.mode.LzRequest;
import cn.lz.security.filter.mode.LzResponse;

/**
 * SpringBoot基础执行链
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 LZ
 * @date 2021/09/17 16:55
 */
public abstract class LzInterfacesFilter extends AbsInterfacesFilterBuilder {

	@Override
	public final boolean doFilter(
			BaseRequest<?> request,
			BaseResponse<?> response,
			LzContext<?, ?> context,
			Object... params
	) throws SecurityException {
		return doFilter(((LzRequest) request), ((LzResponse) response), (SpringBootContextTheadLocal) context, params);
	}

	/**
	 * 进行过滤
	 *
	 * @param lzRequest  请求流
	 * @param lzResponse 响应流
	 * @param params     过滤参数
	 * @return 过滤结果
	 * @throws SecurityException 过滤错误
	 */
	public abstract boolean doFilter(
			LzRequest lzRequest,
			LzResponse lzResponse,
			SpringBootContextTheadLocal context,
			Object... params
	) throws SecurityException;
}
