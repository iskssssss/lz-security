package com.sowell.security.auth.defaults;

import com.sowell.security.auth.service.PasswordEncoder;

import java.util.Objects;

/**
 * @Version 版权 Copyright(c)2021 LZ
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/16 16:52
 */
public class DefaultPasswordEncoder implements PasswordEncoder {
	@Override
	public String encode(CharSequence rawPassword) {
		return rawPassword.toString();
	}

	@Override
	public String decode(CharSequence rawPassword) {
		return rawPassword.toString();
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		return Objects.equals(rawPassword, encodedPassword);
	}
}
