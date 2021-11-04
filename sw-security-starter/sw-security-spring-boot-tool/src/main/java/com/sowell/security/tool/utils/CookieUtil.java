package com.sowell.security.tool.utils;

import com.sowell.tool.core.string.StringUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Version 版权 Copyright(c)2021 LZJ
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/5/31 13:59
 */
public class CookieUtil {

    /**
     * 获取值
     *
     * @param request 请求流
     * @param name    键名
     * @return 值
     */
    public static String getValue(HttpServletRequest request, String name) {
        final Cookie cookie = get(request, name);
        if (cookie == null) {
            return null;
        }
        return cookie.getValue();
    }

    /**
     * 获取Cookie
     *
     * @param request 请求流
     * @param name    键名
     * @return Cookie
     */
    public static Cookie get(HttpServletRequest request, String name) {
        final Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length < 1) {
            return null;
        }
        for (Cookie cookie : cookies) {
            final String cookieName = cookie.getName();
            if (StringUtil.isEmpty(cookieName)) {
                continue;
            }
            if (cookieName.startsWith(name)) {
                return cookie;
            }
        }
        return null;
    }

    /**
     * 设置Cookie
     *
     * @param response 响应流
     * @param name     键名
     * @param value    值
     * @param path     地址
     * @param domain   作用域
     * @param expiry   过期秒数
     */
    public static void set(HttpServletResponse response, String name, String value, String path, String domain, int expiry) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath(StringUtil.isEmpty(path) ? "/" : path);
        if (StringUtil.isNotEmpty(domain)) {
            cookie.setDomain(domain);
        }
        cookie.setHttpOnly(false);
        cookie.setMaxAge(expiry);
        response.addCookie(cookie);
    }

    /**
     * 删除Cookie
     *
     * @param response 响应流
     * @param name     键名
     */
    public static void remove(HttpServletResponse response, String name) {
        set(response, null, null, null, null, 0);
    }
}
