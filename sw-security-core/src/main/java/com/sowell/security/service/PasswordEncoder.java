package com.sowell.security.service;

/**
 * @Version 版权 Copyright(c)2021 LZJ
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/6/24 14:22
 */
public interface PasswordEncoder {

    String encode(CharSequence rawPassword);

    String decode(CharSequence rawPassword);

    boolean matches(CharSequence rawPassword, String encodedPassword);
}
