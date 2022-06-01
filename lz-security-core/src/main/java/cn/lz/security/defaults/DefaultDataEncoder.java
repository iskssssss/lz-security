package cn.lz.security.defaults;

import cn.lz.security.LzCoreManager;
import cn.lz.security.handler.DataEncoder;
import cn.lz.tool.encrypt.model.SwPrivateKey;

/**
 * 默认请求加解密处理器
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 LZ
 * @date 2021/10/26 17:21
 */
public class DefaultDataEncoder implements DataEncoder {

	@Override
	public byte[] encrypt(byte[] bytes) {
		final SwPrivateKey priKey = LzCoreManager.getLzConfig().getEncryptConfig().getPrivateKeyStr();
		final byte[] encryptResult = priKey.encrypt(bytes);
		return encryptResult;
	}

	@Override
	public byte[] decrypt(byte[] bytes) {
		final SwPrivateKey privateKey = LzCoreManager.getLzConfig().getEncryptConfig().getPrivateKeyStr();
		final byte[] decryptResult = privateKey.decrypt(bytes);
		return decryptResult;
	}
}
