package com.sowell.tool.sql;

import com.sowell.tool.fun.SFunction;

import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;

/**
 * @Version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/02 11:06
 */
public class SqlUtil {

	/**
	 * 将SFunction转换为SerializedLambda
	 *
	 * @param function lambda表达式
	 * @param <T>      类型
	 * @return SerializedLambda
	 */
	private static <T> SerializedLambda toSerializedLambda(SFunction<T, ?> function) {
		try {
			final Method writeReplace = function.getClass().getDeclaredMethod("writeReplace");
			writeReplace.setAccessible(true);
			final Object invoke = writeReplace.invoke(function);
			return ((SerializedLambda) invoke);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * get Lambda表达式 转 下划线
	 *
	 * @param func1 get Lambda表达式
	 * @param <T>   类型1
	 * @param <K>   类型2
	 * @return 下划线
	 */
	public static <T, K> String to_snake_cose(SFunction<T, K> func1) {
		return to_snake_cose(func1, null);
	}

	/**
	 * get Lambda表达式 转 下划线
	 *
	 * @param func1 get Lambda表达式
	 * @param alias 数据表别名
	 * @param <T>   类型1
	 * @param <K>   类型2
	 * @return 下划线
	 */
	public static <T, K> String to_snake_cose(SFunction<T, K> func1, String alias) {
		final SerializedLambda serializedLambda = toSerializedLambda(func1);
		if (serializedLambda == null) {
			return null;
		}
		final String implMethodName = serializedLambda.getImplMethodName();
		final String fieldName = implMethodName.substring(3);
		final int length = fieldName.length();
		if (length < 1) {
			return null;
		}
		StringBuilder fieldNameStringBuilder = new StringBuilder();
		if (alias != null) {
			fieldNameStringBuilder.append(alias).append(".");
		}
		for (int i = 0; i < length; i++) {
			final char c = fieldName.charAt(i);
			if (c >= 65 && c <= 90) {
				if (i != 0) {
					fieldNameStringBuilder.append("_");
				}
				fieldNameStringBuilder.append((char) (c + 32));
				continue;
			}
			fieldNameStringBuilder.append(c);
		}
		return fieldNameStringBuilder.toString();
	}
}
