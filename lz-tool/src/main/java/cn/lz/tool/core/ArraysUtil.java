package cn.lz.tool.core;

import cn.lz.tool.core.string.StringUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Version 版权 Copyright(c)2021 LZ
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/03 14:20
 */
public class ArraysUtil {

	public static List<String> toList(String text, String splitChar) {
		return toList(text, splitChar, false);
	}

	public static List<String> toList(String text, String splitChar, boolean isDistinct) {
		if (StringUtil.isEmpty(text) || StringUtil.isEmpty(splitChar)) {
			return Collections.emptyList();
		}
		final String[] splitList = text.split(splitChar);

		final List<String> list = Arrays.asList(splitList);
		if (isDistinct) {
			return list.stream().filter(StringUtil::isNotEmpty).distinct().collect(Collectors.toList());
		}
		return list.stream().filter(StringUtil::isNotEmpty).collect(Collectors.toList());
	}

	public static String toListString(List<String> textList, String splitChar) {
		if (StringUtil.isEmpty(textList)) {
			return null;
		}
		final String listString = textList.stream().map(x -> splitChar + x).collect(Collectors.joining());
		return listString.substring(1);
	}
}
