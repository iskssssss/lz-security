package com.sowell.security.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;

/**
 * @Version 版权 Copyright(c)2021 浙江设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/7/14 9:37
 */
public final class ByteUtil {

    /**
     * 对象 转 byte[]
     *
     * @param o 待转对象
     * @return byte[]
     * @throws IOException id异常
     */
    public static byte[] toBytes(Object o) {
        try (
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)
        ) {
            objectOutputStream.writeObject(o);
            objectOutputStream.flush();
            return outputStream.toByteArray();
        } catch (IOException ioException) {
            return null;
        }
    }

    /**
     * 字符串 转 byte[]
     *
     * @param data 待转字符串
     * @return byte[]
     */
    public static byte[] toBytes(String data) {
        if (StringUtil.isEmpty(data)) {
            return null;
        }
        return data.getBytes(StandardCharsets.UTF_8);
    }
}
