package cn.lz.security.tool.context;

import cn.lz.security.context.model.LzStorage;
import cn.lz.security.tool.mode.LzRequest;
import cn.lz.tool.reflect.model.ControllerMethod;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @Version 版权 Copyright(c)2021 LZ
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/10 17:37
 */
public class LzSpringStorage extends LzStorage<HttpServletRequest> {

	private final ControllerMethod controllerMethod;

	public LzSpringStorage(
			LzRequest request,
			long startRequestTime
	) {
		super(request, startRequestTime);
		this.controllerMethod = request.getControllerMethod();
	}

	@Override
	public void close() throws IOException {
		super.request = null;
		super.userAgentInfo = null;
	}

	public ControllerMethod getControllerMethod() {
		return controllerMethod;
	}
}
