package com.sowell.demo.filter.config.security.handler;

import com.sowell.security.context.model.BaseRequest;
import com.sowell.security.handler.EncodeSwitchHandler;
import com.sowell.tool.core.string.StringUtil;
import org.springframework.stereotype.Component;

/**
 * TODO
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 LZ
 * @date 2021/11/08 10:51
 */
//@Component
public class TestEncodeSwitchHandler implements EncodeSwitchHandler {
	@Override
	public boolean decrypt(
			BaseRequest<?> request
	) {
		//System.out.println("TestEncryptSwitchHandler.decrypt(...)");
		final String decrypt = request.getHeader("decrypt");
		if (StringUtil.isEmpty(decrypt)) {
			return false;
		}
		return decrypt.intern() == "true";
	}

	@Override
	public boolean encrypt(
			BaseRequest<?> request
	) {
		//System.out.println("TestEncryptSwitchHandler.encrypt(...)");
		final String encrypt = request.getHeader("encrypt");
		if (StringUtil.isEmpty(encrypt)) {
			return false;
		}
		return encrypt.intern() == "true";
	}
}
