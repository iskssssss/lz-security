package cn.lz.security.filter.context;

import cn.lz.security.context.model.Storage;
import cn.lz.security.filter.mode.LzRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * SpringBoot办存储器
 * @Version 版权 Copyright(c)2021 LZ
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/10 17:37
 */
public class SpringBootStorage extends Storage<HttpServletRequest> {

	public SpringBootStorage(
			LzRequest request,
			long startRequestTime
	) {
		super(request, startRequestTime);
	}

	/**
	 * 获取请求流
	 *
	 * @return 请求流
	 */
	@Override
	public LzRequest getRequest() {
		return ((LzRequest) super.getRequest());
	}

	@Override
	public void close() throws IOException {
		super.userAgentInfo = null;
	}
}
