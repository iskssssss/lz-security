package com.sowell.security.utils;

import com.alibaba.fastjson.JSONObject;
import com.sowell.common.core.web.result.ICode;
import com.sowell.security.exception.SecurityException;
import com.sowell.security.wrapper.HttpServletResponseWrapper;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @Version 版权 Copyright(c)2021 浙江设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/7/1 11:52
 */
public class ServletUtil extends cn.hutool.extra.servlet.ServletUtil {

    /**
     * 用于获取当前访问的接口
     */
    protected static final UrlPathHelper URL_PATH_HELPER = new UrlPathHelper();
    /**
     * 用于访问接口验证
     */
    protected static final PathMatcher PATH_MATCHER = new AntPathMatcher();

    public static ServletRequestAttributes getServletRequestAttributes() {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        return (ServletRequestAttributes) attributes;
    }

    /**
     * 获取 Request
     *
     * @return HttpServletRequest
     */
    public static HttpServletRequest getRequest() {
        return getServletRequestAttributes().getRequest();
    }

    /**
     * 获取 Request
     *
     * @return HttpServletRequest
     */
    public static HttpServletResponse getResponse() {
        return getServletRequestAttributes().getResponse();
    }

    /**
     * 发送响应信息
     */
    public static void printResponse(
            ICode ajaxEntity
    ) {
        final HttpServletResponse response = ServletUtil.getResponse();
        printResponse(response, ajaxEntity);
    }

    /**
     * 发送异常信息
     */
    public static void printResponse(
            HttpServletResponse response,
            ICode rCode
    ) {
        JSONObject resultJson = new JSONObject();
        resultJson.put("code", rCode.getCode());
        resultJson.put("data", null);
        resultJson.put("message", rCode.getMessage());
        final String json = resultJson.toJSONString();
        final byte[] jsonBytes = json.getBytes(StandardCharsets.UTF_8);
        printResponse(response, jsonBytes);
    }

    /**
     * 发送异常信息
     */
    public static void printResponse(
            HttpServletResponse response,
            byte[] jsonBytes
    ) {
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/json;charset=utf-8");
        try (final ServletOutputStream outputStream = response.getOutputStream()) {
            final int length = jsonBytes.length;
            response.setContentLength(length);
            outputStream.write(jsonBytes, 0, length);
        } catch (IOException e) {
            throw new SecurityException("发送响应信息异常", e);
        }
    }

    /**
     * 发送异常信息
     */
    public static void printResponse(
            ServletResponse response,
            byte[] jsonBytes
    ) {
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/json;charset=utf-8");
        try (final ServletOutputStream outputStream = response.getOutputStream()) {
            response.setContentLength(jsonBytes.length);
            outputStream.write(jsonBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取响应数据
     *
     * @param response 响应数据
     * @return 响应数据JSON
     */
    public static byte[] getResponseDataBytes(
            HttpServletResponse response,
            byte[] errorBytes
    ) {
        byte[] bytes = null;
        if (!(response instanceof HttpServletResponseWrapper)) {
            return null;
        }
        if (errorBytes == null) {
            HttpServletResponseWrapper responseWrapper = (HttpServletResponseWrapper) response;
            bytes = responseWrapper.toByteArray();
        }
        if (bytes == null || bytes.length < 1) {
            return null;
        }
        return bytes;
    }

    public static String getLookupPathForRequest(HttpServletRequest request) {
        return URL_PATH_HELPER.getLookupPathForRequest(request);
    }

    public static boolean urlMatch(String pattern, String path) {
        return PATH_MATCHER.match(pattern, path);
    }

    /**
     * 校验ip
     * <p>IP白名单支持IP段的格式（如X.X.X.X/X）</p>
     *
     * @param pattern  配对ip
     * @param clientIp 请求客户端ip
     * @return 是否匹配
     */
    public static boolean checkIp(
            String pattern,
            String clientIp
    ) {
        if (!pattern.contains("/")) {
            return pattern.equals(clientIp);
        }
        final String[] ipList = pattern.split("/");
        final String whiteIp = ipList[0];
        final int maskNumber = Integer.parseInt(ipList[1]);
        if (maskNumber >= 32) {
            return clientIp.equals(whiteIp);
        }
        int mask = maskNumber / 8;
        int len = Math.min(clientIp.length(), whiteIp.length());
        for (int i = 0; i < len; i++) {
            final char a = whiteIp.charAt(i);
            final char b = clientIp.charAt(i);
            if (a != b) {
                return false;
            }
            if (a != '.') {
                continue;
            }
            if (--mask == 0) {
                break;
            }
        }
        return true;
    }
}
