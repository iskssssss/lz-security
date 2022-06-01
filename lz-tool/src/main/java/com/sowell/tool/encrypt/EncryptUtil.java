package com.sowell.tool.encrypt;

import com.sowell.tool.core.string.StringUtil;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 加密工具类
 * @Author: sowell
 * @Date: 2021/6/2 13:53
 */
public class EncryptUtil {

    /**
     * MD5 加密
     *
     * @param text 加密文本
     * @return 加密后的信息
     */
    public static String md5(String text) {
        try {
            byte[] secretBytes = MessageDigest.getInstance("md5").digest(text.getBytes());
            StringBuilder md5code = new StringBuilder(new BigInteger(1, secretBytes).toString(16));
            for (int i = 0; i < 32 - md5code.length(); i++) {
                md5code.insert(0, "0");
            }
            return md5code.toString();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    /**
     * 生成Sign
     *
     * @param appKey    appKey
     * @param appSecret appSecret
     * @param timestamp 时间戳
     * @return Sign
     */
    public static String sign(
            String appKey,
            String appSecret,
            Long timestamp
    ) {
        if (StringUtil.isEmpty(appKey) || StringUtil.isEmpty(appSecret) || timestamp == null) {
            return null;
        }
        return EncryptUtil.md5(appKey + appSecret + timestamp);
    }
}
