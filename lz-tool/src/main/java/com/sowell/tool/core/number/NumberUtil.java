package com.sowell.tool.core.number;

/**
 * @Version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/10/21 14:20
 */
public class NumberUtil {

	public static boolean checkNumber(Object obj) {
		if (obj instanceof Number) {
			return true;
		}
		if (obj instanceof String) {
			return checkNumber(((String) obj));
		}
		return false;
	}

	public static boolean checkNumber(String str) {
		final int length = str.length();
		for (int i = 0, xsdCount = 0; i < length; i++) {
			final char c = str.charAt(i);
			if (c >= '0' && c <= '9') {
				continue;
			}
			if (c != '.') {
				return false;
			}
			xsdCount++;
			if (xsdCount > 1 || i + 1 >= length) {
				return false;
			}
			final char c1 = str.charAt(i + 1);
			if (c1 < '0' || c1 > '9') {
				return false;
			}
		}
		return true;
	}
}
