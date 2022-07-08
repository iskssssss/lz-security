package cn.lz.security.auth.filters;

import cn.lz.security.exception.base.SecurityException;
import cn.lz.security.filter.context.SpringBootContextTheadLocal;
import cn.lz.security.log.LzLogger;
import cn.lz.security.log.LzLoggerUtil;
import cn.lz.security.filter.filters.LzInterfacesFilter;
import cn.lz.security.filter.mode.LzRequest;
import cn.lz.security.filter.mode.LzResponse;

/**
 * 角色/权限验证过滤器
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2022 LZJ
 * @date 2022/7/1 11:15
 */
public class ResourceFilter extends LzInterfacesFilter {
    protected final static LzLogger logger = LzLoggerUtil.getLzLogger(ResourceFilter.class);

    @Override
    public void init() {
        logger.info("角色/权限验证过滤器 - init");
    }

    @Override
    public void destroy() {
        logger.info("角色/权限验证过滤器 - destroy");
    }

    @Override
    public boolean doFilter(LzRequest lzRequest, LzResponse lzResponse, SpringBootContextTheadLocal context, Object... params) throws SecurityException {
        logger.info("角色/权限验证过滤器 - doFilter");
        return super.next(params);
    }
}
