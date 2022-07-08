package cn.lz.security.auth.filters;

import cn.lz.security.arrays.UrlHashSet;
import cn.lz.security.auth.LzAuthManager;
import cn.lz.security.auth.annotation.AnonymousCheck;
import cn.lz.security.exception.base.SecurityException;
import cn.lz.security.filter.context.SpringBootContextTheadLocal;
import cn.lz.security.filter.filters.LzInterfacesFilter;
import cn.lz.security.filter.mode.LzRequest;
import cn.lz.security.filter.mode.LzResponse;
import cn.lz.security.log.LzLogger;
import cn.lz.security.log.LzLoggerUtil;
import cn.lz.security.token.AccessTokenUtil;
import cn.lz.tool.core.enums.AuthCode;
import cn.lz.tool.reflect.model.ControllerMethod;

/**
 * 匿名接口过滤器
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2022 杭州设维信息技术有限公司
 * @date 2022/7/1 11:14
 */
public class AnonymousFilter extends LzInterfacesFilter {
    protected final static LzLogger logger = LzLoggerUtil.getLzLogger(AnonymousFilter.class);

    @Override
    public void init() {
        logger.info("匿名接口过滤器 - init");
    }

    @Override
    public void destroy() {
        logger.info("匿名接口过滤器 - destroy");
    }

    @Override
    public boolean doFilter(LzRequest lzRequest, LzResponse lzResponse, SpringBootContextTheadLocal context, Object... params) throws SecurityException {
        logger.info("匿名接口过滤器 - doFilter");
        String requestPath = lzRequest.getRequestPath();
        final ControllerMethod controllerMethod = lzRequest.getControllerMethod();
        final AnonymousCheck anonymousCheck = controllerMethod.getMethodAndControllerAnnotation(AnonymousCheck.class);
        UrlHashSet anonymousUrls = LzAuthManager.getAuthConfigurer().getAnonymousUrlList();
        if ((anonymousCheck != null && anonymousCheck.open()) || anonymousUrls.containsPath(requestPath)) {
            if (AccessTokenUtil.checkExpiration()) {
                return yes();
            }
            return no(AuthCode.ANONYMOUS);
        }
        return super.next(params);
    }
}
