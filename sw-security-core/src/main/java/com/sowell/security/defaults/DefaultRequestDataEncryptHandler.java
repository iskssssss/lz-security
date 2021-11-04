package com.sowell.security.defaults;

import com.sowell.security.IcpCoreManager;
import com.sowell.security.handler.RequestDataEncryptHandler;
import com.sowell.tool.encrypt.model.SwPrivateKey;

/**
 * 默认请求加解密处理器
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @date 2021/10/26 17:21
 */
public class DefaultRequestDataEncryptHandler implements RequestDataEncryptHandler {

	@Override
	public String encrypt(byte[] bytes) {
		final SwPrivateKey priKey = IcpCoreManager.getIcpConfig().getEncryptConfig().getPrivateKey();
		final String encryptResult = priKey.encrypt(bytes);
		return encryptResult;
	}

	@Override
	public Object decrypt(byte[] bytes) {
		final SwPrivateKey privateKey = IcpCoreManager.getIcpConfig().getEncryptConfig().getPrivateKey();
		final Object decryptResult = privateKey.decrypt(bytes);
		return decryptResult;
	}
}
