package com.sowell.security.utils;

import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * @Version 版权 Copyright(c)2021 浙江设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/7/12 12:45
 */
public class StringUtil extends StringUtils {

    /**
     * 判断字符串是否为空.
     *
     * @param object 对象
     * @return 是否为空
     */
    public static boolean isEmpty(Object object) {
        return isNull(object) || "".equals(object);
    }

    /**
     * 判断对象是否不为空.
     *
     * @param object 对象
     * @return 是否不为空
     */
    public static boolean isNotEmpty(String object) {
        return !isEmpty(object);
    }

    /**
     * 判断对象是否为null
     *
     * @param object 对象
     * @return 是否为null
     */
    public static boolean isNull(Object object) {
        return null == object;
    }

    /**
     * 判断对象是否不为null
     *
     * @param object 对象
     * @return 是否为null
     */
    public static boolean isNotNull(Object object) {
        return !isNull(object);
    }

    /**
     * char -> String
     *
     * @param c char数据
     * @return String
     */
    public static String toString(char c) {
        char[] data = {c};
        return toString(data);
    }

    /**
     * char[] -> String
     *
     * @param chars char[]数据
     * @return String
     */
    public static String toString(char[] chars) {
        return String.valueOf(chars);
    }

    /**
     * String -> char[]
     *
     * @param str String数据
     * @return char[]
     */
    public static char[] toArray(String str) {
        return str.toCharArray();
    }

    /**
     * Number -> String
     *
     * @param val String数据
     * @return char[]
     */
    public static String toString(Number val) {
        return String.valueOf(val);
    }

    /**
     * String -> Long
     *
     * @param val String数据
     * @return char[]
     */
    public static Long toLong(String val) throws NumberFormatException {
        return Long.valueOf(val);
    }

    /**
     * 删除头部字符
     *
     * @param val 字符串
     * @return 结果
     */
    public static String delHeadChar(String val) {
        return val.substring(1);
    }

    /**
     * 删除尾部字符
     *
     * @param val 字符串
     * @return 结果
     */
    public static String delTailChar(String val) {
        return val.substring(0, val.length() - 1);
    }

    /**
     * 删除头部和尾部字符
     *
     * @param val 字符串
     * @return 结果
     */
    public static String delHeadAndTailChar(String val) {
        return delHeadChar(delTailChar(val));
    }


    public static String toString(InputStream inputStream) {
        try {
            StringBuilder sb = new StringBuilder();
            byte[] bytes = new byte[1024];
            int len;
            while ((len = inputStream.read(bytes)) > 0) {
                sb.append(new String(bytes, 0, len));
            }
            return sb.toString();
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * 字节数据转字符串
     *
     * @param bodyBytes 字节数据
     * @return 字符串
     */
    public static String byte2String(byte[] bodyBytes) {
        return new String(bodyBytes, StandardCharsets.UTF_8);
    }
}
