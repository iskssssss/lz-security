package com.sowell.security.filter;

import com.sowell.security.filter.base.AbsInterfacesFilterBuilder;
import com.sowell.security.context.model.BaseRequest;
import com.sowell.security.context.model.BaseResponse;
import com.sowell.security.exception.SecurityException;
import com.sowell.security.log.IcpLogger;
import com.sowell.security.log.IcpLoggerUtil;

/**
 * @Version 版权 Copyright(c)2021 浙江设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/6/25 12:23
 */
public class StartFilter extends AbsInterfacesFilterBuilder {
    protected final IcpLogger icpLogger = IcpLoggerUtil.getIcpLogger(StartFilter.class);

    @Override
    public void init() { }

    @Override
    public boolean doFilter(BaseRequest<?> request, BaseResponse<?> response, Object... params) throws SecurityException {
        icpLogger.info("过滤接口：" + request.getRequestPath());
        return super.next(request, response, params);
    }

    @Override
    public void destroy() { }
}
