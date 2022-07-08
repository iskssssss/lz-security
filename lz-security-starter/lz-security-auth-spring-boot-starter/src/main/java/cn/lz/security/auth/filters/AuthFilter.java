package cn.lz.security.auth.filters;

import cn.lz.security.arrays.UrlHashSet;
import cn.lz.security.auth.LzAuthManager;
import cn.lz.security.auth.annotation.AuthCheck;
import cn.lz.security.exception.base.SecurityException;
import cn.lz.security.filter.context.SpringBootContextTheadLocal;
import cn.lz.security.log.LzLogger;
import cn.lz.security.log.LzLoggerUtil;
import cn.lz.security.token.AccessTokenUtil;
import cn.lz.security.filter.filters.LzInterfacesFilter;
import cn.lz.security.filter.mode.LzRequest;
import cn.lz.security.filter.mode.LzResponse;
import cn.lz.tool.core.enums.AuthCode;
import cn.lz.tool.reflect.model.ControllerMethod;

/**
 * 认证接口过滤器
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2022 LZJ
 * @date 2022/7/1 11:14
 */
public class AuthFilter extends LzInterfacesFilter {
    protected final static LzLogger logger = LzLoggerUtil.getLzLogger(AuthFilter.class);

    @Override
    public void init() {
        logger.info("认证接口过滤器 - init");
    }

    @Override
    public void destroy() {
        logger.info("认证接口过滤器 - destroy");
    }

    @Override
    public boolean doFilter(LzRequest lzRequest, LzResponse lzResponse, SpringBootContextTheadLocal context, Object... params) throws SecurityException {
        logger.info("认证接口过滤器 - doFilter");
        String requestPath = lzRequest.getRequestPath();
        final ControllerMethod controllerMethod = lzRequest.getControllerMethod();
        final AuthCheck authCheck = controllerMethod.getMethodAndControllerAnnotation(AuthCheck.class);
        UrlHashSet authUrlList = LzAuthManager.getAuthConfigurer().getAuthUrlList();
        if ((authCheck != null && authCheck.open()) || authUrlList.containsPath(requestPath)) {
            if (AccessTokenUtil.checkExpiration()) {
                return no(AuthCode.AUTHORIZATION);
            }
        }
        return super.next(params);
    }
}
