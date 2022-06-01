package cn.lz.tool.encrypt;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @Version 版权 Copyright(c)2021 LZ
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/10/14 14:22
 */
public final class Base64Util {

	private static Base64.Decoder decoder() {
		return Base64.getDecoder();
	}
	private static Base64.Encoder encoder() {
		return Base64.getEncoder();
	}

	/**
	 * 解码
	 *
	 * @param src 生成的Base64编码字符的字节数组
	 * @return 解码后的字节数组
	 */
	public static byte[] decode(byte[] src) {
		try {
			return decoder().decode(src);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("(" + new String(src) + ")不是有效的Base64", e);
		}
	}

	/**
	 * 解码
	 *
	 * @param str Base64编码字符的字符串
	 * @return 解码后的字节数组
	 */
	public static byte[] decode(String str) {
		return decode(str.getBytes(StandardCharsets.UTF_8));
	}

	/**
	 * Base64编码
	 *
	 * @param src 原文字节
	 * @return 生成的Base64编码字符的字符串
	 */
	public static String encodeToString(byte[] src) {
		return encoder().encodeToString(src);
	}

	/**
	 * Base64编码
	 *
	 * @param str 原文
	 * @return 生成的Base64编码字符的字符串
	 */
	public static String encodeToString(String str) {
		return encodeToString(str.getBytes(StandardCharsets.UTF_8));
	}

	/**
	 * Base64编码
	 *
	 * @param src 原文字节
	 * @return 生成的Base64编码字符的字节数组
	 */
	public static byte[] encode(byte[] src) {
		return encoder().encode(src);
	}

	/**
	 * Base64编码
	 *
	 * @param str 原文
	 * @return 生成的Base64编码字符的字节数组
	 */
	public static byte[] encode(String str) {
		return encode(str.getBytes(StandardCharsets.UTF_8));
	}
}
