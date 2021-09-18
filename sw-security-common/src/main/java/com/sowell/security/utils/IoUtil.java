package com.sowell.security.utils;

import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.util.StrUtil;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * @Version 版权 Copyright(c)2021 浙江设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/7/12 13:08
 */
public final class IoUtil extends cn.hutool.core.io.IoUtil {

    /**
     * 从{@link BufferedReader}中读取String
     *
     * @param reader {@link BufferedReader}
     * @return String
     * @throws IORuntimeException IO异常
     */
    public static String readReset(BufferedReader reader) throws IORuntimeException {
        final StringBuilder builder = StrUtil.builder();
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            return builder.toString();
        } catch (IOException ignored) {

        }
        return "";
    }
}
