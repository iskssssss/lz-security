package com.sowell.security.filter;

import com.sowell.security.base.AbstractInterfacesFilter;
import com.sowell.security.context.model.BaseRequest;
import com.sowell.security.context.model.BaseResponse;
import com.sowell.security.exception.SecurityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Version 版权 Copyright(c)2021 浙江设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/6/25 12:23
 */
public class StartFilter extends AbstractInterfacesFilter {
    protected final Logger logger = LoggerFactory.getLogger(StartFilter.class);

    @Override
    public void init() {
    }

    @Override
    public boolean doFilter(
            BaseRequest<?> request,
            BaseResponse<?> response,
            Object... params
    ) throws SecurityException {
        logger.info("============================== 访问接口过滤开始 ==============================");
        return super.next(request, response, params);
    }

    @Override
    public void destroy() {

    }
}
