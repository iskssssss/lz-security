package cn.lz.tool.core.string;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

/**
 * 字符串工具类
 * @Author: lz
 * @Date: 2021/6/2 13:53
 */
public class StringUtil {

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
    public static boolean isNotEmpty(Object object) {
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

    /**
     * 将输入流转换为字符串
     *
     * @param inputStream 输入流
     * @return 字符串
     */
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

    /**
     * 将字符串第一个字符转换为大写
     *
     * @param text 字符串
     * @return 结果
     */
    public static String toUpperCaseForFirst(String text) {
        return StringUtil.toUpperCase(text, 1, 1);
    }

    /**
     * 将指定范围内的字符串转换为大写
     *
     * @param text   字符串
     * @param start  开始位置
     * @param length 长度
     * @return 结果
     */
    public static String toUpperCase(String text, int start, int length) {
        final int startIndex = start - 1;
        final int endIndex = startIndex + length;
        final String substring = text.substring(startIndex, endIndex);
        final String substring1 = text.substring(endIndex);
        if (startIndex != 0) {
            return text.substring(0, startIndex) + substring.toUpperCase(Locale.ROOT) + substring1;
        } else {
            return substring.toUpperCase(Locale.ROOT) + substring1;
        }
    }

    /**
     * 将字符串第一个字符转换为小写
     *
     * @param text 字符串
     * @return 结果
     */
    public static String toLowerCaseForFirst(String text) {
        return StringUtil.toLowerCase(text, 1, 1);
    }

    /**
     * 将指定范围内的字符串转换为小写
     *
     * @param text   字符串
     * @param start  开始位置
     * @param length 长度
     * @return 结果
     */
    public static String toLowerCase(String text, int start, int length) {
        final int startIndex = start - 1;
        final int endIndex = startIndex + length;
        final String substring = text.substring(startIndex, endIndex);
        final String substring1 = text.substring(endIndex);
        if (startIndex != 0) {
            return text.substring(0, startIndex) + substring.toLowerCase(Locale.ROOT) + substring1;
        } else {
            return substring.toLowerCase(Locale.ROOT) + substring1;
        }
    }

    /**
     * 删除指定字符
     *
     * @param text 字符串
     * @param c    字符
     * @return 结果字符串
     */
    public static String delChar(String text, char c) {
        if (StringUtil.isEmpty(text)) {
            return null;
        }
        final int length = text.length();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            final char c1 = text.charAt(i);
            if (c1 == c) {
                continue;
            }
            sb.append(c1);
        }
        return sb.toString();
    }

    /**
     * 删除所有空格
     *
     * @param text 字符串
     * @return 结果字符串
     */
    public static String delAllSpace(String text) {
        return StringUtil.delChar(text, ' ');
    }

    /**
     * 判断字符串是否全是空格
     *
     * @param text 字符串
     * @return 是否全是空格
     */
    public static boolean isAllSpace(String text) {
        final int length = text.length();
        for (int i = 0; i < length; i++) {
            final char c = text.charAt(i);
            if (c != ' ') {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断字符串是否全是空格
     *
     * @param text 字符串
     * @return 是否全是空格
     */
    public static boolean notAllSpace(String text) {
        return !isAllSpace(text);
    }

    /**
     * 删除指定字符串
     *
     * @param accessToken 源字符串
     * @param delStr      删除字符串
     * @return 结果字符串
     */
    public static String delString(String accessToken, String delStr) {
        if (StringUtil.isEmpty(delStr)) {
            return accessToken;
        }
        return accessToken.replaceAll(delStr, "");
    }

    /**
     * 字符串前缀匹配
     *
     * @param source     源字符串
     * @param prefixList 前缀列表
     * @return 是否配对
     */
    public static boolean startsWith(String source, String... prefixList) {
        if (prefixList == null || prefixList.length < 1) {
            return false;
        }
        for (String prefix : prefixList) {
            if (source.startsWith(prefix)) {
                return true;
            }
        }
        return false;
    }
}
