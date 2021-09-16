package com.sowell.security.utils;

import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentParser;
import com.sowell.security.model.UserAgentInfo;

/**
 * @Version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/10 14:23
 */
public class UserAgentUtil {

	public static UserAgentInfo getUserAgentInfo(String ua) {
		UserAgent userAgent = UserAgentParser.parse(ua);
		return new UserAgentInfo() {{
			setUa(ua);
			setBrowser(userAgent.getBrowser().getName());
			setPlatform(userAgent.getPlatform().getName());
			setIsMobile(userAgent.isMobile() ? 1 : 0);
			setBrowserVersion(userAgent.getVersion());
			setOs(userAgent.getOs().getName());
		}};
	}
}
