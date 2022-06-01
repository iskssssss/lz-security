package com.sowell.security.tool.context;

import com.sowell.security.context.IcpContextTheadLocal;
import org.springframework.util.PathMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * spring版 上下文
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @date 2021/11/09 14:02
 */
public class SpringContextTheadLocal extends IcpContextTheadLocal<HttpServletRequest, HttpServletResponse> {
	private final PathMatcher pathMatcher;

	public SpringContextTheadLocal(PathMatcher pathMatcher) {
		this.pathMatcher = pathMatcher;
	}

	@Override
	public boolean matchUrl(String pattern, String path) {
		return this.pathMatcher.match(pattern, path);
	}
}
