package com.sowell.security.base;

import com.sowell.security.IcpConstant;
import com.sowell.security.IcpManager;
import com.sowell.security.annotation.LogBeforeFilter;
import com.sowell.security.context.IcpContext;
import com.sowell.security.context.model.BaseRequest;
import com.sowell.security.context.model.BaseResponse;
import com.sowell.tool.core.enums.RCode;
import com.sowell.security.exception.SecurityException;
import com.sowell.security.log.BaseFilterLogHandler;
import com.sowell.security.log.IcpLogger;
import com.sowell.security.log.IcpLoggerUtil;
import com.sowell.security.utils.ServletUtil;
import com.sowell.tool.http.enums.ContentTypeEnum;

import java.io.IOException;

/**
 * @Version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/10/22 15:50
 */
public class InterfacesFilterBuilder {
	protected final IcpLogger icpLogger = IcpLoggerUtil.getIcpLogger(InterfacesFilterBuilder.class);
	private AbstractInterfacesFilter nextFilter;

	/**
	 * 获取下一过滤器
	 *
	 * @return 下一过滤器
	 */
	public AbstractInterfacesFilter getNextFilter() {
		return nextFilter;
	}

	/**
	 * 链接下一过滤器
	 *
	 * @param nextFilter 下一过滤器
	 * @return 下一过滤器
	 */
	public final AbstractInterfacesFilter linkFilter(AbstractInterfacesFilter nextFilter) {
		this.nextFilter = nextFilter;
		return nextFilter;
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
			return discharged(request);
		}
		// 日志处理
		final Class<? extends AbstractInterfacesFilter> nextFilterClass = nextFilter.getClass();
		final LogBeforeFilter logBeforeFilter = nextFilterClass.getAnnotation(LogBeforeFilter.class);
		if (logBeforeFilter != null) {
			final IcpContext<?, ?> icpContext = IcpManager.getIcpContext();
			final BaseFilterLogHandler filterLogHandler = IcpManager.getFilterLogHandler();
			final Object logEntity = filterLogHandler.beforeHandler(request, response);
			// 暂缓
			icpContext.setAttribute(IcpConstant.LOG_SWITCH, true);
			icpContext.setAttribute(IcpConstant.LOG_ENTITY_CACHE_KEY, logEntity);
		}
		return nextFilter.doFilter(request, response, params);
	}

	/**
	 * 拦截接口
	 *
	 * @param request 请求流
	 * @return 拦截
	 */
	protected final boolean headOff(BaseRequest<?> request) {
		icpLogger.info("拦截接口：" + request.getRequestPath());
		return false;
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
	protected final boolean headOff(BaseRequest<?> request, BaseResponse<?> response, RCode rCode) throws SecurityException {
		ServletUtil.printResponse(response, ContentTypeEnum.JSON.name, rCode);
		icpLogger.info("拦截信息：" + rCode.getMessage());
		headOff(request);
		return false;
	}

	/**
	 * 放行接口
	 *
	 * @param request 请求流
	 * @return 放行
	 */
	protected final boolean discharged(BaseRequest<?> request) {
		icpLogger.info("放行接口：" + request.getRequestPath());
		return true;
	}
}
