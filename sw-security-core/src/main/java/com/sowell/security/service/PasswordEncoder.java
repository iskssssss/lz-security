package com.sowell.security.service;

/**
 * @Version 版权 Copyright(c)2021 浙江设维信息技术有限公司
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
