package cn.lz.security.tool.context;

import cn.lz.security.context.LzContext;
import cn.lz.security.context.LzSecurityContextThreadLocal;
import cn.lz.security.context.model.BaseRequest;
import cn.lz.security.context.model.BaseResponse;
import cn.lz.security.context.model.Storage;
import org.springframework.util.PathMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * SpringBoot版 上下文
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 LZ
 * @date 2021/11/09 14:02
 */
public class SpringBootContextTheadLocal implements LzContext<HttpServletRequest, HttpServletResponse> {
	private final PathMatcher pathMatcher;

	public SpringBootContextTheadLocal(PathMatcher pathMatcher) {
		this.pathMatcher = pathMatcher;
	}

	@Override
	public final BaseRequest<HttpServletRequest> getRequest() {
		BaseRequest<?> servletRequest = LzSecurityContextThreadLocal.getRequest();
		return ((BaseRequest<HttpServletRequest>) servletRequest);
	}

	@Override
	public final BaseResponse<HttpServletResponse> getResponse() {
		BaseResponse<?> servletResponse = LzSecurityContextThreadLocal.getResponse();
		return ((BaseResponse<HttpServletResponse>) servletResponse);
	}

	@Override
	public final Storage<HttpServletRequest> getStorage() {
		Storage<?> lzStorage = LzSecurityContextThreadLocal.getLzStorage();
		return ((Storage<HttpServletRequest>) lzStorage);
	}

	@Override
	public boolean matchUrl(String pattern, String path) {
		return this.pathMatcher.match(pattern, path);
	}
}
