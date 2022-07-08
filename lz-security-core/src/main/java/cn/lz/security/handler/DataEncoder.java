package cn.lz.security.handler;

import java.util.Map;

/**
 * 数据加解密处理器
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 LZ
 * @date 2021/10/26 17:13
 */
public interface DataEncoder {

	/**
	 * 初始化
	 *
	 * @param params 参数
	 */
	void init(Map<String, String> params);

	/**
	 * 加密
	 *
	 * @param bytes 待加密字节数组
	 * @return 密文
	 */
	default byte[] encrypt(byte[] bytes) {
		return bytes;
	}

	/**
	 * 解密
	 *
	 * @param bytes 带解密字节数组
	 * @return 明文
	 */
	default byte[] decrypt(byte[] bytes) {
		return bytes;
	}
}
