//package com.sowell.security.filter;
//
//import com.sowell.security.base.AbstractInterfacesFilter;
//import com.sowell.security.context.model.BaseRequest;
//import com.sowell.security.context.model.BaseResponse;
//import com.sowell.security.exception.SecurityException;
//import com.sowell.security.log.IcpLogger;
//import com.sowell.security.log.IcpLoggerUtil;
//
//import java.util.List;
//
///**
// * @Version 版权 Copyright(c)2021 浙江设维信息技术有限公司
// * @ClassName:
// * @Descripton:
// * @Author: 孔胜
// * @Date: 2021/7/19 11:19
// */
//public class ExcludeFilter extends AbstractInterfacesFilter {
//    protected final IcpLogger icpLogger = IcpLoggerUtil.getIcpLogger(AbstractInterfacesFilter.class);
//
//    private List<String> excludeUrls = null;
//
//    private List<String> excludeUrls() {
//        if (this.excludeUrls == null) {
//            this.excludeUrls = super.filterConfigurer.filterUrl().excludeUrls;
//        }
//        return this.excludeUrls;
//    }
//
//    @Override
//    public void init() {
//        icpLogger.info("exclude filter init.");
//    }
//
//    @Override
//    public boolean doFilter(
//            BaseRequest<?> request,
//            BaseResponse<?> response,
//            Object... params
//    ) throws SecurityException {
//        final List<String> excludeUrls = excludeUrls();
//        for (String excludeUrl : excludeUrls) {
//            final String securityInterface = excludeUrl.replace(" ", "");
//            if (request.isPath(securityInterface)) {
//                return discharged(request);
//            }
//        }
//        return next(request, response, params);
//    }
//
//    @Override
//    public void destroy() {
//        icpLogger.info("exclude filter destroy.");
//        this.excludeUrls = null;
//    }
//}
