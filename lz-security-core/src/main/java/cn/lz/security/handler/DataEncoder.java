package cn.lz.security.handler;

/**
 * 数据加解密处理器
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 LZ
 * @date 2021/10/26 17:13
 */
public interface DataEncoder {

	/**
	 * 加密
	 *
	 * @param bytes 待加密字节数组
	 * @return 密文
	 */
	byte[] encrypt(byte[] bytes);

	/**
	 * 解密
	 *
	 * @param bytes 带解密字节数组
	 * @return 明文
	 */
	byte[] decrypt(byte[] bytes);
}