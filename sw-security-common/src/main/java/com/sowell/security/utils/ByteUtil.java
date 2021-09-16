package com.sowell.security.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * @Version 版权 Copyright(c)2021 浙江设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/7/14 9:37
 */
public class ByteUtil {

    /**
     * 对象 转 byte[]
     *
     * @param o 待转对象
     * @return byte[]
     * @throws IOException id异常
     */
    public static byte[] object2bytes(Object o) throws IOException {
        try (
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)
        ) {
            objectOutputStream.writeObject(o);
            objectOutputStream.flush();
            return outputStream.toByteArray();
        }
    }
}
