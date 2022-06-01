package com.sowell.security.utils;

import com.sowell.security.context.model.BaseRequest;
import com.sowell.tool.core.string.StringUtil;

/**
 * @Version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/17 14:52
 */
public final class IpUtil extends com.sowell.tool.core.IpUtil {
	private static final int IPV4_MAX_LENGTH = 15;
	private static final String UNKNOWN = "unknown";

	/**
	 * 获取ip地址
	 *
	 * @param request http请求
	 * @return ipv4
	 */
	public static String getIp(BaseRequest<?> request) {
		if (request == null) {
			return UNKNOWN;
		}
		String ip = request.getHeader("x-forwarded-for");
		if (StringUtil.isNull(ip) || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (StringUtil.isNull(ip) || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Forwarded-For");
		}
		if (StringUtil.isNull(ip) || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (StringUtil.isNull(ip) || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Real-IP");
		}
		if (StringUtil.isNull(ip) || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if (StringUtil.isNull(ip)) {
			return UNKNOWN;
		}
		if (ip.length() > IPV4_MAX_LENGTH) {
			if (ip.indexOf(',') == -1) {
				return ip;
			}
			ip = ip.substring(0, ip.indexOf(','));
		}
		return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
	}
}