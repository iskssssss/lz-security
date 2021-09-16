package com.sowell.security.filter;

import com.sowell.security.utils.ServletUtil;
import com.sowell.security.base.AbstractInterfacesFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @Version 版权 Copyright(c)2021 浙江设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/7/19 11:19
 */
public class ExcludeFilter extends AbstractInterfacesFilter {
    protected final Logger logger = LoggerFactory.getLogger(ExcludeFilter.class);

    private List<String> excludeUrls = null;

    private List<String> getExcludeUrls() {
        if (this.excludeUrls == null) {
            this.excludeUrls = super.filterConfigurer.filterUrl().excludeUrls;
        }
        return this.excludeUrls;
    }

    @Override
    public void init() {
        logger.info("匿名过滤器初始化。");
    }

    @Override
    public boolean doFilter(
            HttpServletRequest request,
            HttpServletResponse response,
            Object... params
    ) throws IOException, IllegalAccessException, ServletException {
        logger.info("开始过滤匿名访问接口");
        String lookupPath = ServletUtil.getLookupPathForRequest(request);
        for (String excludeUrl : getExcludeUrls()) {
            final String securityInterface = excludeUrl.replace(" ", "");
            if (ServletUtil.urlMatch(securityInterface, lookupPath)) {
                return discharged(request);
            }
        }
        return next(request, response, params);
    }

    @Override
    public void destroy() {
        logger.info("匿名过滤器销毁。");
    }
}
