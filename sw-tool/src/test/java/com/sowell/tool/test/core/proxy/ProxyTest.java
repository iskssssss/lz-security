package com.sowell.tool.test.core.proxy;

import com.sowell.tool.test.core.proxy.model.ILogSave;
import com.sowell.tool.test.core.proxy.model.LogSave;
import org.junit.Test;

/**
 * TODO
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @date 2021/11/01 09:51
 */
public class ProxyTest {

	@Test
	public void test() {
		final ILogSave proxyInstance = LogHandler.getProxyInstance(new LogSave());
		proxyInstance.save();
	}
}
