package cn.lz.security.auth.service;

/**
 * @Version 版权 Copyright(c)2021 LZ
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/6/24 14:22
 */
public interface CredentialEncoder {

    /**
     * 密码加密
     *
     * @param rawPassword 原文
     * @return 密文
     */
    String encode(CharSequence rawPassword);

    /**
     * 密码解密
     *
     * @param rawPassword 密文
     * @return 原文
     */
    String decode(CharSequence rawPassword);

    /**
     * 密码匹配
     *
     * @param rawPassword     原始密码
     * @param encodedPassword 密文
     * @return 是否匹配
     */
    boolean matches(CharSequence rawPassword, String encodedPassword);
}
