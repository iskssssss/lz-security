package cn.lz.tool.http;

import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentParser;
import cn.lz.tool.http.model.UserAgentInfo;

/**
 * UserAgent工具类
 * @Version 版权 Copyright(c)2021 LZ
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/10 14:23
 */
public final class UserAgentUtil {

	/**
	 * 解析ua中的内容
	 *
	 * @param ua ua
	 * @return 解析后获取到的内容
	 */
	public static UserAgentInfo getUserAgentInfo(String ua) {
		return UserAgentUtil.getUserAgentInfo(ua, null);
	}

	/**
	 * 解析ua中的内容
	 *
	 * @param ua     ua
	 * @param ipAddr IP地址
	 * @return 解析后获取到的内容
	 */
	public static UserAgentInfo getUserAgentInfo(String ua, String ipAddr) {
		UserAgent userAgent = UserAgentParser.parse(ua);
		final UserAgentInfo userAgentInfo = new UserAgentInfo();
		userAgentInfo.setUa(ua);
		userAgentInfo.setBrowser(userAgent.getBrowser().getName());
		userAgentInfo.setPlatform(userAgent.getPlatform().getName());
		userAgentInfo.setIsMobile(userAgent.isMobile() ? 1 : 0);
		userAgentInfo.setBrowserVersion(userAgent.getVersion());
		userAgentInfo.setOs(userAgent.getOs().getName());
		userAgentInfo.setIpAddr(ipAddr);
		return userAgentInfo;
	}
}
