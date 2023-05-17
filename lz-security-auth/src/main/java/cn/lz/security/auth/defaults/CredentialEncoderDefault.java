package cn.lz.security.auth.defaults;

import cn.lz.security.auth.service.CredentialEncoder;

import java.util.Objects;

/**
 * @Version 版权 Copyright(c)2021 LZ
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/16 16:52
 */
public class CredentialEncoderDefault implements CredentialEncoder {
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
