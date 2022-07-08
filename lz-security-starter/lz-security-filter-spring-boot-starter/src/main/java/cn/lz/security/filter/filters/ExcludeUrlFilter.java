package cn.lz.security.filter.filters;

import cn.lz.security.annotation.ExcludeInterface;
import cn.lz.security.annotation.IncludeInterface;
import cn.lz.security.arrays.UrlHashSet;
import cn.lz.security.exception.base.SecurityException;
import cn.lz.security.filter.LzFilterManager;
import cn.lz.security.filter.context.SpringBootContextTheadLocal;
import cn.lz.security.filter.mode.LzRequest;
import cn.lz.security.filter.mode.LzResponse;
import cn.lz.security.log.LzLogger;
import cn.lz.security.log.LzLoggerUtil;
import cn.lz.tool.reflect.model.ControllerMethod;

/**
 * 开放接口过滤器
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2022 杭州设维信息技术有限公司
 * @date 2022/7/1 11:13
 */
public class ExcludeUrlFilter extends LzInterfacesFilter {
    protected final static LzLogger logger = LzLoggerUtil.getLzLogger(ExcludeUrlFilter.class);

    @Override
    public void init() {
        logger.info("开放接口过滤器 - init");
    }

    @Override
    public void destroy() {
        logger.info("开放接口过滤器 - destroy");
    }

    @Override
    public boolean doFilter(LzRequest lzRequest, LzResponse lzResponse, SpringBootContextTheadLocal context, Object... params) throws SecurityException {
        logger.info("开放接口过滤器 - doFilter");
        final ControllerMethod controllerMethod = lzRequest.getControllerMethod();
        // 获取开放/拦截注解
        ExcludeInterface excludeInterface = null;
        IncludeInterface includeInterface = null;
        if (controllerMethod != null) {
            excludeInterface = controllerMethod.getMethodAndControllerAnnotation(ExcludeInterface.class);
            includeInterface = controllerMethod.getMethodAndControllerAnnotation(IncludeInterface.class);
        }
        final String requestPath = lzRequest.getRequestPath();
        // 校验
        if (this.isExclude(excludeInterface, requestPath) || this.isInclude(includeInterface, requestPath)) {
            return super.yes();
        }
        return super.next(params);
    }

    /**
     * 判断当前接口是否处于开放状态
     *
     * @param excludeInterface 开放注解
     * @param requestPath      当前请求接口
     * @return 是否进行下一步过滤
     */
    private boolean isExclude(ExcludeInterface excludeInterface, String requestPath) {
        // 当开放注解不为空时 判断 当前注解是否生效
        if (excludeInterface != null) {
            // 生效直接结束过滤
            if (excludeInterface.open()) {
                return true;
            }
            // 未生效进行下一步过滤
            return false;
        }
        // 获取开放接口列表
        UrlHashSet excludeUrls = LzFilterManager.getFilterConfigurer().getExcludeUrls();
        // 该接口在开放接口列表中直接结束过滤
        if (excludeUrls.containsPath(requestPath)) {
            return true;
        }
        return false;
    }

    /**
     * 判断当前接口是否处于拦截状态
     *
     * @param includeInterface 拦截注解
     * @param requestPath      当前请求接口
     * @return 是否进行下一步过滤
     */
    private boolean isInclude(IncludeInterface includeInterface, String requestPath) {
        if (includeInterface != null) {
            // 生效进行下一步过滤
            if (includeInterface.open()) {
                return false;
            }
            // 未生效直接结束过滤
            return true;
        }
        // 获取拦截接口列表
        UrlHashSet includeUrls = LzFilterManager.getFilterConfigurer().getIncludeUrls();
        if (includeUrls.containsPath(requestPath)) {
            // 该接口在拦截接口列表中进行下一步过滤
            return false;
        }
        // 结束过滤
        return true;
    }
}
