package com.sowell.security.defaults;

import com.sowell.security.LzCoreManager;
import com.sowell.security.handler.DataEncoder;
import com.sowell.tool.encrypt.model.SwPrivateKey;

/**
 * 默认请求加解密处理器
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 LZ
 * @date 2021/10/26 17:21
 */
public class DefaultDataEncoder implements DataEncoder {

	@Override
	public String encrypt(byte[] bytes) {
		final SwPrivateKey priKey = LzCoreManager.getLzConfig().getEncryptConfig().getPrivateKeyStr();
		final String encryptResult = priKey.encrypt(bytes);
		return encryptResult;
	}

	@Override
	public Object decrypt(byte[] bytes) {
		final SwPrivateKey privateKey = LzCoreManager.getLzConfig().getEncryptConfig().getPrivateKeyStr();
		final Object decryptResult = privateKey.decrypt(bytes);
		return decryptResult;
	}
}
