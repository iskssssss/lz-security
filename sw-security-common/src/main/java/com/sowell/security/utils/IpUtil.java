package com.sowell.security.utils;

/**
 * @Version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/17 14:52
 */
public final class IpUtil {

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
