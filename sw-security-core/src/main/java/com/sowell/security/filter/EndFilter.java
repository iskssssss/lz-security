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
 * @Date: 2021/6/25 15:24
 */
public class EndFilter extends AbstractInterfacesFilter {
    protected final Logger logger = LoggerFactory.getLogger(EndFilter.class);

    @Override
    public void init() {

    }

    @Override
    public boolean doFilter(
            BaseRequest<?> request,
            BaseResponse<?> response,
            Object... params
    ) throws SecurityException {
        logger.info("放行接口：" + request.getRequestPath());
        logger.info("============================== 访问接口过滤结束 ==============================");
        return true;
    }

    @Override
    public void destroy() {

    }
}