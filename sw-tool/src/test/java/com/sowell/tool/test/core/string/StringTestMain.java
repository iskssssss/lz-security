package com.sowell.tool.test.core.string;

import com.sowell.tool.core.string.StringUtil;
import org.junit.Test;

/**
 * TODO
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @date 2021/10/27 09:53
 */
public class StringTestMain {

	@Test
	public void test() {
		String text = "1 2 3 4";
		System.out.println(StringUtil.delAllSpace(text));
	}
}
