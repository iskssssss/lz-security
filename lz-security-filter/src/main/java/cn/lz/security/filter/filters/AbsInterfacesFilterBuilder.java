package cn.lz.security.filter.filters;

import cn.lz.security.LzCoreManager;
import cn.lz.security.annotation.LogBeforeFilter;
import cn.lz.security.context.LzContext;
import cn.lz.security.context.model.BaseRequest;
import cn.lz.security.context.model.BaseResponse;
import cn.lz.security.exception.base.SecurityException;
import cn.lz.security.filter.LzFilterManager;
import cn.lz.security.log.BaseFilterLogHandler;
import cn.lz.security.log.LzLogger;
import cn.lz.security.log.LzLoggerUtil;
import cn.lz.security.utils.ServletUtil;
import cn.lz.security.LzConstant;
import cn.lz.tool.core.enums.RCode;
import cn.lz.tool.http.enums.MediaType;

import java.io.IOException;

/**
 * 过滤器抽象类
 * <p>通过此类来实现自定义接口过滤</p>
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 LZ
 * @date 2021/10/22 15:50
 */
public abstract class AbsInterfacesFilterBuilder implements IInterfacesFilter {
	protected final LzLogger lzLogger = LzLoggerUtil.getLzLogger(AbsInterfacesFilterBuilder.class);
	private IInterfacesFilter nextFilter;

	/**
	 * 获取下一过滤器
	 *
	 * @return 下一过滤器
	 */
	public IInterfacesFilter getNextFilter() {
		return nextFilter;
	}

	/**
	 * 链接下一过滤器
	 *
	 * @param nextFilter 下一过滤器
	 * @return 下一过滤器
	 */
	public final IInterfacesFilter linkFilter(IInterfacesFilter nextFilter) {
		this.nextFilter = nextFilter;
		return nextFilter;
	}

	/**
	 * 进入下一步过滤
	 *
	 * @param params 过滤参数
	 * @return 过滤结果
	 * @throws SecurityException 过滤错误
	 */
	protected final boolean next(Object... params) throws SecurityException {
		final BaseRequest<?> request = LzCoreManager.getLzContext().getRequest();
		final BaseResponse<?> response = LzCoreManager.getLzContext().getResponse();
		return nextFilter.doFilter(request, response, params);
	}

	/**
	 * 进入下一步过滤
	 *
	 * @param request  请求流
	 * @param response 响应流
	 * @param params   过滤参数
	 * @return 过滤结果
	 * @throws SecurityException 过滤错误
	 */
	protected final boolean next(BaseRequest<?> request, BaseResponse<?> response, Object... params) throws SecurityException {
		if (null == nextFilter) {
			return yes(request);
		}
		// 日志处理
		final Class<? extends IInterfacesFilter> nextFilterClass = nextFilter.getClass();
		final LogBeforeFilter logBeforeFilter = nextFilterClass.getAnnotation(LogBeforeFilter.class);
		if (logBeforeFilter != null) {
			final LzContext<?, ?> lzContext = LzCoreManager.getLzContext();
			final BaseFilterLogHandler filterLogHandler = LzFilterManager.getFilterLogHandler();
			final Object logEntity = filterLogHandler.before(request, response);
			// 暂缓
			lzContext.setAttribute(LzConstant.LOG_SWITCH, true);
			lzContext.setAttribute(LzConstant.LOG_ENTITY_CACHE_KEY, logEntity);
		}
		return nextFilter.doFilter(request, response, params);
	}

	/**
	 * 拦截接口
	 *
	 * @return 拦截
	 */
	protected final boolean no() {
		return no(RCode.REQUEST_ERROR);
	}

	/**
	 * 拦截接口并返回信息
	 *
	 * @param rCode 响应信息
	 * @return
	 * @throws IOException
	 */
	protected final boolean no(RCode rCode) throws SecurityException {
		return no(LzCoreManager.getLzContext().getRequest(), LzCoreManager.getLzContext().getResponse(), rCode);
	}

	/**
	 * 拦截接口并返回信息
	 *
	 * @param request  请求流
	 * @param response 响应流
	 * @param rCode    响应信息
	 * @return
	 * @throws IOException
	 */
	protected final boolean no(BaseRequest<?> request, BaseResponse<?> response, RCode rCode) throws SecurityException {
		ServletUtil.printResponse(response, MediaType.APPLICATION_JSON_VALUE, rCode);
		lzLogger.error("拦截接口：" + request.getRequestPath());
		lzLogger.error("拦截信息：" + rCode.getMessage());
		return false;
	}

	/**
	 * 放行接口
	 *
	 * @return 放行
	 */
	protected final boolean yes() {
		return yes(LzCoreManager.getLzContext().getRequest());
	}

	/**
	 * 放行接口
	 *
	 * @param request 请求流
	 * @return 放行
	 */
	protected final boolean yes(BaseRequest<?> request) {
		lzLogger.info("放行接口：" + request.getRequestPath());
		return true;
	}
}
