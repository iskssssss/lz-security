package cn.lz.security.tool.context;

import cn.lz.security.context.LzContextTheadLocal;
import org.springframework.util.PathMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * spring版 上下文
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 LZ
 * @date 2021/11/09 14:02
 */
public class SpringContextTheadLocal extends LzContextTheadLocal<HttpServletRequest, HttpServletResponse> {
	private final PathMatcher pathMatcher;

	public SpringContextTheadLocal(PathMatcher pathMatcher) {
		this.pathMatcher = pathMatcher;
	}

	@Override
	public boolean matchUrl(String pattern, String path) {
		return this.pathMatcher.match(pattern, path);
	}
}
