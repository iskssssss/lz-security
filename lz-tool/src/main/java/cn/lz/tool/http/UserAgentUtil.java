package cn.lz.tool.http;

import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentParser;
import cn.lz.tool.http.model.UserAgentInfo;

/**
 * @Version 版权 Copyright(c)2021 LZ
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/10 14:23
 */
public final class UserAgentUtil {

	public static UserAgentInfo getUserAgentInfo(String ua) {
		UserAgent userAgent = UserAgentParser.parse(ua);
		final UserAgentInfo userAgentInfo = new UserAgentInfo();
		userAgentInfo.setUa(ua);
		userAgentInfo.setBrowser(userAgent.getBrowser().getName());
		userAgentInfo.setPlatform(userAgent.getPlatform().getName());
		userAgentInfo.setIsMobile(userAgent.isMobile() ? 1 : 0);
		userAgentInfo.setBrowserVersion(userAgent.getVersion());
		userAgentInfo.setOs(userAgent.getOs().getName());
		return userAgentInfo;
	}
}