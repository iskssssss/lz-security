package com.sowell.security.base;

import com.sowell.security.IcpConstant;
import com.sowell.security.IcpManager;
import com.sowell.security.annotation.LogBeforeFilter;
import com.sowell.security.config.FilterConfigurer;
import com.sowell.security.context.IcpContext;
import com.sowell.security.context.model.BaseRequest;
import com.sowell.security.context.model.BaseResponse;
import com.sowell.security.enums.RCode;
import com.sowell.security.exception.SecurityException;
import com.sowell.security.log.BaseFilterLogHandler;
import com.sowell.security.log.IcpLogger;
import com.sowell.security.log.IcpLoggerUtil;
import com.sowell.security.utils.ServletUtil;

import java.io.IOException;

/**
 * @Version 版权 Copyright(c)2021 浙江设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/7/7 11:26
 */
public abstract class AbstractInterfacesFilter {
    protected final IcpLogger icpLogger = IcpLoggerUtil.getIcpLogger(AbstractInterfacesFilter.class);
    protected final FilterConfigurer filterConfigurer = IcpManager.getFilterConfigurer();

    private AbstractInterfacesFilter nextFilter;

    /**
     * 链接下一过滤器
     */
    public final AbstractInterfacesFilter linkFilter(AbstractInterfacesFilter nextFilter) {
        this.nextFilter = nextFilter;
        return nextFilter;
    }

    /**
     * 进入下一步过滤
     */
    protected final boolean next(
            BaseRequest<?> request,
            BaseResponse<?> response,
            Object... params
    ) throws SecurityException {
        if (null == nextFilter) {
            return true;
        }
        // 日志处理
        final Class<? extends AbstractInterfacesFilter> nextFilterClass = nextFilter.getClass();
        final LogBeforeFilter logBeforeFilter = nextFilterClass.getAnnotation(LogBeforeFilter.class);
        if (logBeforeFilter != null) {
            final IcpContext<Object, Object> icpContext = IcpManager.getIcpContext();
            final BaseFilterLogHandler filterLogHandler = IcpManager.getFilterLogHandler();
            final Object logEntity = filterLogHandler.beforeHandler(request, response);
            // 暂缓
            icpContext.setAttribute(IcpConstant.LOG_ENTITY_CACHE_KEY, logEntity);
        }

        return nextFilter.doFilter(request, response, params);
    }

    public final boolean hasNext() {
        return nextFilter != null;
    }

    public final AbstractInterfacesFilter next() {
        return nextFilter;
    }

    /**
     * 初始化
     */
    public abstract void init();

    /**
     * 过滤
     */
    public abstract boolean doFilter(
            BaseRequest<?> request,
            BaseResponse<?> response,
            Object... params
    ) throws SecurityException;

    /**
     * 是否认证
     */
    public boolean isAuth(BaseRequest<?> request) {
        return true;
    }

    /**
     * 销毁
     */
    public abstract void destroy();

    /**
     * 拦截接口
     *
     * @param request 请求流
     * @return 拦截
     */
    public final boolean headOff(BaseRequest<?> request) {
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
    public final boolean headOff(
            BaseRequest<?> request,
            BaseResponse<?> response,
            RCode rCode
    ) throws SecurityException {
        ServletUtil.printResponse(response, rCode);
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
    public final boolean discharged(BaseRequest<?> request) {
        icpLogger.info("放行接口：" + request.getRequestPath());
        return true;
    }
}
