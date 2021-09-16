package com.sowell.security;//package com.sowell.security;
//
//import com.sowell.security.annotation.LogBeforeFilter;
//import com.sowell.security.annotation.RecordRequestData;
//import com.sowell.security.annotation.RecordResponseData;
//import com.sowell.security.utils.BeanUtil;
//import com.sowell.security.utils.ServletUtil;
//import com.sowell.security.utils.SpringUtil;
//import com.sowell.security.base.AbstractFilter;
//import com.sowell.security.base.AbstractInterfacesFilter;
//import com.sowell.security.handler.FilterDataHandler;
//import com.sowell.security.wrapper.HttpServletRequestWrapper;
//import com.sowell.security.wrapper.HttpServletResponseWrapper;
//import lombok.extern.slf4j.Slf4j;
//
//import javax.servlet.*;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.lang.reflect.Method;
//import java.util.Map;
//
///**
// * @Version 版权 Copyright(c)2021 浙江设维信息技术有限公司
// * @ClassName:
// * @Descripton:
// * @Author: 孔胜
// * @Date: 2021/7/7 11:23
// */
//@Slf4j
//public class FilterContainer extends AbstractFilter {
//    private FilterDataHandler filterDataHandler;
//    /**
//     * 过滤链
//     */
//    private AbstractInterfacesFilter interfacesFilter;
//
//    @Override
//    public void init(
//            FilterConfig filterConfig
//    ) throws ServletException {
//        log.info("过滤器中心初始化。");
//        final Map<String, Object> beansWithAnnotation = SpringUtil.getBeansWithAnnotation(LogBeforeFilter.class);
//        if (beansWithAnnotation.size() > 1) {
//            throw new RuntimeException("注解(LogBeforeFilter)全局只可存在一个。");
//        }
//        final Map<String, Method> controllerMethodMap = BeanUtil.initControllerMethodMap();
//        IcpManager.setInterfacesMethodMap(controllerMethodMap);
//        filterDataHandler = new FilterDataHandler();
//        interfacesFilter = IcpManager.getInterfacesFilter();
//        AbstractInterfacesFilter aif = interfacesFilter;
//        while (aif.hasNext()) {
//            aif.init();
//            aif = aif.next();
//        }
//    }
//
//    @Override
//    public void doFilter(
//            ServletRequest request,
//            ServletResponse response,
//            FilterChain chain
//    ) throws IOException, ServletException {
//        request.setCharacterEncoding("utf-8");
//        HttpServletRequest requestWrapper = (HttpServletRequest) request;
//        HttpServletResponse responseWrapper = (HttpServletResponse) response;
//        final String token = requestWrapper.getHeader("Authorization");
////        SecurityUtil.setContext(request, response, token);
//        // 获取当前访问接口
//        String lookupPath = ServletUtil.getLookupPathForRequest(requestWrapper);
//        // 获取当前访问接口对应方法信息
//        final Method method = IcpManager.getMethodByInterfaceUrl(lookupPath);
//        // 开始过滤
//        Exception exception = null;
//        byte[] responseBytes = null;
//        try {
//            RecordResponseData recordResponseData = null;
//            RecordRequestData recordRequestData = null;
//            if (method != null) {
//                recordResponseData = method.getAnnotation(RecordResponseData.class);
//                recordRequestData = method.getAnnotation(RecordRequestData.class);
//            }
//            // 判断当前接口是否记录请求数据
//            if (recordRequestData != null) {
//                requestWrapper = new HttpServletRequestWrapper(requestWrapper);
//            }
//            // 判断当前接口是否记录响应数据
//            if (recordResponseData != null) {
//                responseWrapper = new HttpServletResponseWrapper(responseWrapper);
//            }
//            // 过滤
//            if (interfacesFilter.doFilter(requestWrapper, responseWrapper)) {
//                chain.doFilter(requestWrapper, responseWrapper);
//            }
//            // 获取响应数据
//            if (responseWrapper instanceof HttpServletResponseWrapper) {
//                responseBytes = ((HttpServletResponseWrapper) responseWrapper).toByteArray();
//            }
//        } catch (Exception e) {
//            exception = e;
//        } finally {
//            //SecurityUtil.removeContext();
//            // 日志处理
//            final byte[] handlerBytes = filterDataHandler.handler(requestWrapper, responseWrapper, exception);
//            if (exception != null && handlerBytes != null) {
//                responseBytes = handlerBytes;
//            }
//            if (responseBytes != null) {
//                response.resetBuffer();
//                ServletUtil.printResponse(response, responseBytes);
//            }
//        }
//    }
//
//    @Override
//    public void destroy() {
//        log.info("过滤器中心销毁。");
//        AbstractInterfacesFilter aif = interfacesFilter;
//        while (aif.hasNext()) {
//            aif.destroy();
//            aif = aif.next();
//        }
//    }
//}
