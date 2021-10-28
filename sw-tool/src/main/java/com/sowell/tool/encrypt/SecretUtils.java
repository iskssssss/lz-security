package com.sowell.tool.encrypt;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.SecureUtil;

import java.security.KeyPair;

/**
 * @version 版权 Copyright(c)2021 浙江设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @author: 孔胜
 * @date: 2021/5/6 18:18
 */
public class SecretUtils {

    /**
     * 获取RSA非对称加密 密钥对
     *
     * @return 密钥对
     */
    private static KeyPair generateKeyPairByRSA() {
        return SecureUtil.generateKeyPair("RSA");
    }

    /**
     * 获取Base64密钥
     *
     * @param keyPair 密钥对
     * @return Base64密钥
     */
    private static String getPrivateKeyBase64(KeyPair keyPair) {
        return Base64.encode(keyPair.getPrivate().getEncoded());
    }

    /**
     * 获取Base64公钥
     *
     * @param keyPair 密钥对
     * @return Base64公钥
     */
    private static String getPublicKeyBase64(KeyPair keyPair) {
        return Base64.encode(keyPair.getPublic().getEncoded());
    }

    public static String randomSeed() {
        return IdUtil.fastSimpleUUID() + System.currentTimeMillis();
    }

    public static String appKey() {
        return appKey(randomSeed());
    }

    public static String appSecret() {
        return appSecret(randomSeed());
    }

    public static String appKey(String seed) {
        return SecureUtil.sha1(seed);
    }

    public static String appSecret(String seed) {
        return SecureUtil.sha256(seed);
    }

}
