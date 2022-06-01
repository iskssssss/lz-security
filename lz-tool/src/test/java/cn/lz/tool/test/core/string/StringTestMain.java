package cn.lz.tool.test.core.string;

import cn.lz.tool.core.string.StringUtil;
import org.junit.Test;

import java.util.UUID;

/**
 * TODO
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 LZ
 * @date 2021/10/27 09:53
 */
public class StringTestMain {

	@Test
	public void test() {
		System.out.println(UUID.randomUUID());
		String text = "1 2 3 4";
		System.out.println(StringUtil.delAllSpace(text));
	}

	@Test
	public void delString() {
		System.out.println(UUID.randomUUID());
		String text = "1 2 3 4";
		System.out.println(StringUtil.delString(text, " 2 3 "));
	}
}
