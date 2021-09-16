package com.sowell.security.filter;

import com.sowell.security.base.AbstractInterfacesFilter;
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
 * @Date: 2021/6/25 12:23
 */
public class StartFilter extends AbstractInterfacesFilter {
    protected final Logger logger = LoggerFactory.getLogger(StartFilter.class);

    @Override
    public void init() {
    }

    @Override
    public boolean doFilter(
            HttpServletRequest request,
            HttpServletResponse response,
            Object... params
    ) throws IOException, IllegalAccessException, ServletException {
        logger.info("============================== 访问接口过滤开始 ==============================");
        return super.next(request, response, params);
    }

    @Override
    public void destroy() {

    }
}
