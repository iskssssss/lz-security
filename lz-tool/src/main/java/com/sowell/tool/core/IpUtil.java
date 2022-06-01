package com.sowell.tool.core;

/**
 * @Version 版权 Copyright(c)2021 LZ
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/17 14:52
 */
public class IpUtil {

	/**
	 * 校验ip
	 * <p>IP白名单支持IP段的格式（如X.X.X.X/X）</p>
	 *
	 * @param pattern  配对ip
	 * @param clientIp 请求客户端ip
	 * @return 是否匹配
	 */
	public static boolean checkIp(String pattern, String clientIp) {
		String whiteIp = pattern;
		int maskNumber = 32;
		if (pattern.contains("/")) {
			final String[] ipList = pattern.split("/");
			 whiteIp = ipList[0];
			 maskNumber = Integer.parseInt(ipList[1]);
		}
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
