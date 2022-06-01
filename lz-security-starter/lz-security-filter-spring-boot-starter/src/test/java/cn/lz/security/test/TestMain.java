package cn.lz.security.test;

import cn.lz.tool.jwt.model.AuthDetails;

/**
 * @Version 版权 Copyright(c)2021 LZ
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/23 09:03
 */
public class TestMain extends AuthDetails<TestMain> {

	public static void main(String[] args) {
		//SecurityUtil.setAuthDetails(new TestMain());
	}

	@Override
	public Class<TestMain> setSourceClass() {
		final Class<? extends TestMain> aClass = this.getClass();
		return TestMain.class;
	}
}
