package com.sowell.security.base;

import com.sowell.common.core.web.result.ICode;
import com.sowell.security.annotation.LogBeforeFilter;
import com.sowell.security.utils.ServletUtil;
import com.sowell.security.IcpConstant;
import com.sowell.security.IcpManager;
import com.sowell.security.config.FilterConfigurer;
import com.sowell.security.context.IcpContextHandler;
import com.sowell.security.log.BaseFilterLogHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Version 版权 Copyright(c)2021 浙江设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/7/7 11:26
 */
public abstract class AbstractInterfacesFilter {
    protected final Logger logger = LoggerFactory.getLogger(AbstractInterfacesFilter.class);
    protected final FilterConfigurer filterConfigurer = IcpManager.getFilterConfigurer();

    private AbstractInterfacesFilter nextFilter;

    /**
     * 链接下一过滤器
     */
    public AbstractInterfacesFilter linkFilter(AbstractInterfacesFilter nextFilter) {
        this.nextFilter = nextFilter;
        return nextFilter;
    }

    /**
     * 进入下一步过滤
     */
    protected boolean next(
            HttpServletRequest request,
            HttpServletResponse response,
            Object... params
    ) throws IOException, IllegalAccessException, ServletException {
        if (null == nextFilter) {
            return true;
        }
        // 日志处理
        if (IcpContextHandler.isSaveRequestLog()) {
            final Class<? extends AbstractInterfacesFilter> nextFilterClass = nextFilter.getClass();
            final LogBeforeFilter logBeforeFilter = nextFilterClass.getAnnotation(LogBeforeFilter.class);
            if (logBeforeFilter != null) {
                final BaseFilterLogHandler filterLogHandler = IcpManager.getFilterLogHandler();
                final Object logEntity = filterLogHandler.beforeHandler(request);
                // 暂缓
                IcpContextHandler.setAttribute(IcpConstant.LOG_ENTITY_CACHE_KEY, logEntity);
            }
        }
        return nextFilter.doFilter(request, response, params);
    }

    public boolean hasNext() {
        return nextFilter != null;
    }

    public AbstractInterfacesFilter next() {
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
            HttpServletRequest request,
            HttpServletResponse response,
            Object... params
    ) throws IOException, IllegalAccessException, ServletException;

    /**
     * 是否认证
     */
    public boolean isAuth(HttpServletRequest request) {
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
    public boolean headOff(HttpServletRequest request) {
        String lookupPath = ServletUtil.getLookupPathForRequest(request);
        logger.info("拦截接口：" + lookupPath);
        logger.info("============================== 访问接口过滤结束 ==============================");
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
    public boolean headOff(
            HttpServletRequest request,
            HttpServletResponse response,
            ICode rCode
    ) throws IOException {
        ServletUtil.printResponse(response, rCode);
        logger.info("拦截信息：" + rCode.getMessage());
        headOff(request);
        return false;
    }

    /**
     * 放行接口
     *
     * @param request 请求流
     * @return 放行
     */
    public boolean discharged(HttpServletRequest request) {
        String lookupPath = ServletUtil.getLookupPathForRequest(request);
        logger.info("放行接口：" + lookupPath);
        logger.info("访问接口过滤结束。");
        logger.info("============================== 访问接口过滤结束 ==============================");
        return true;
    }
}
