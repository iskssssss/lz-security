package cn.lz.tool.encrypt;

import cn.lz.tool.core.bytes.ByteUtil;
import cn.lz.tool.encrypt.model.SwKeyPair;
import org.apache.commons.lang.ArrayUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author 孔胜
 */
public class RSAEncryptUtil {

	/**
	 * RSA最大加密明文大小
	 */
	private static final int MAX_ENCRYPT_BLOCK = 117;
	/**
	 * RSA最大解密密文大小
	 */
	private static final int MAX_DECRYPT_BLOCK = 128;

	/**
	 * 随机生成密钥对
	 *
	 * @throws NoSuchAlgorithmException 当请求特定加密算法但该算法在环境中不可用时，会引发此异常。
	 */
	public static SwKeyPair genKeyPair() throws NoSuchAlgorithmException {
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
		// 初始化密钥对生成器，密钥大小为96-1024位
		keyPairGen.initialize(1024, new SecureRandom());
		KeyPair keyPair = keyPairGen.generateKeyPair();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		return new SwKeyPair(privateKey, publicKey);
	}

	/**
	 * RSA加密
	 *
	 * @param str         加密字符串
	 * @param key         钥
	 * @param isPublicKey 是否使公钥
	 * @return 密文
	 * @throws Exception 加密过程中的异常信息
	 */
	public static String encryptString(String str, String key, boolean isPublicKey) throws Exception {
		return encryptString(str.getBytes(StandardCharsets.UTF_8), key, isPublicKey);
	}
	/**
	 * RSA加密
	 *
	 * @param bytes       加密字节数组
	 * @param key         钥
	 * @param isPublicKey 是否使公钥
	 * @return 密文
	 * @throws Exception 加密过程中的异常信息
	 */
	public static String encryptString(byte[] bytes, String key, boolean isPublicKey) throws Exception {
		byte[] decoded = Base64Util.decode(key);
		if (isPublicKey) {
			RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
			return encryptString(bytes, pubKey);
		}
		RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
		return encryptString(bytes, priKey);
	}
	/**
	 * RSA解密
	 *
	 * @param str         加密字符串
	 * @param key         钥
	 * @param isPublicKey 是否使公钥
	 * @return 明文
	 * @throws Exception 解密过程中的异常信息
	 */
	public static Object decrypt(String str, String key, boolean isPublicKey) throws Exception {
		return decrypt(str.getBytes(StandardCharsets.UTF_8), key, isPublicKey);
	}
	/**
	 * RSA解密
	 *
	 * @param bytes       加密字节数组
	 * @param key         钥
	 * @param isPublicKey 是否使公钥
	 * @return 明文
	 * @throws Exception 解密过程中的异常信息
	 */
	public static Object decrypt(byte[] bytes, String key, boolean isPublicKey) throws Exception {
		byte[] decoded = Base64Util.decode(key);
		String keyTypeName = "私";
		try {
			if (isPublicKey) {
				keyTypeName = "公";
				RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
				return decrypt(bytes, pubKey);
			}
			RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
			return decrypt(bytes, priKey);
		} catch (InvalidKeySpecException | NoSuchAlgorithmException | IllegalArgumentException e) {
			throw new RuntimeException("无效" + keyTypeName + "钥原文，请检查" + keyTypeName + "钥原文。", e);
		}
	}

	// ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ 公钥加密 and 私钥解密 ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	/**
	 * RSA公钥加密
	 *
	 * @param str    加密字符串
	 * @param pubKey 公钥
	 * @return 密文
	 * @throws Exception 加密过程中的异常信息
	 */
	public static String encryptString(String str, RSAPublicKey pubKey) throws Exception {
		return encryptString(str.getBytes(StandardCharsets.UTF_8), pubKey);
	}
	/**
	 * RSA公钥加密
	 *
	 * @param bytes  加密字节数组
	 * @param pubKey 公钥
	 * @return 密文
	 * @throws Exception 加密过程中的异常信息
	 */
	public static String encryptString(byte[] bytes, RSAPublicKey pubKey) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, pubKey);
		byte[] deBytes = splitByteList(cipher, bytes, MAX_ENCRYPT_BLOCK);
		return Base64Util.encodeToString(deBytes);
	}
	/**
	 * RSA公钥加密
	 *
	 * @param bytes  加密字节数组
	 * @param pubKey 公钥
	 * @return 密文
	 * @throws Exception 加密过程中的异常信息
	 */
	public static byte[] encryptByte(byte[] bytes, RSAPublicKey pubKey) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, pubKey);
		byte[] deBytes = splitByteList(cipher, bytes, MAX_ENCRYPT_BLOCK);
		return Base64Util.encode(deBytes);
	}
	/**
	 * RSA私钥解密
	 *
	 * @param str    解密字符串
	 * @param priKey 私钥
	 * @return 明文
	 * @throws Exception 解密过程中的异常信息
	 */
	public static Object decrypt(String str, RSAPrivateKey priKey) throws Exception {
		return decrypt(str.getBytes(StandardCharsets.UTF_8), priKey);
	}
	/**
	 * RSA私钥解密
	 *
	 * @param bytes  解密字节数组
	 * @param priKey 私钥
	 * @return 明文
	 * @throws Exception 解密过程中的异常信息
	 */
	public static Object decrypt(byte[] bytes, RSAPrivateKey priKey) throws Exception {
		byte[] inputByte = Base64Util.decode(bytes);
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, priKey);
		byte[] deBytes = splitByteList(cipher, inputByte, MAX_DECRYPT_BLOCK);
		return new String(deBytes);
	}
	/**
	 * RSA私钥解密
	 *
	 * @param bytes  解密字节数组
	 * @param priKey 私钥
	 * @return 明文
	 * @throws Exception 解密过程中的异常信息
	 */
	public static byte[] decryptByte(byte[] bytes, RSAPrivateKey priKey) throws Exception {
		byte[] inputByte = Base64Util.decode(bytes);
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, priKey);
		byte[] deBytes = splitByteList(cipher, inputByte, MAX_DECRYPT_BLOCK);
		return deBytes;
	}
	// ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑ 公钥加密 and 私钥解密 ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

	// ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ 私钥加密 and 公钥解密 ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	/**
	 * RSA私钥加密
	 *
	 * @param str    加密字符串
	 * @param priKey 私钥
	 * @return 密文
	 * @throws Exception 加密过程中的异常信息
	 */
	public static String encryptString(String str, RSAPrivateKey priKey) throws Exception {
		return encryptString(str.getBytes(StandardCharsets.UTF_8), priKey);
	}
	/**
	 * RSA私钥加密
	 *
	 * @param bytes  加密字节数组
	 * @param priKey 私钥
	 * @return 密文
	 * @throws Exception 加密过程中的异常信息
	 */
	public static String encryptString(byte[] bytes, RSAPrivateKey priKey) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, priKey);
		int blockSize = cipher.getBlockSize();
		byte[] enBytes = splitByteList(cipher, bytes, MAX_ENCRYPT_BLOCK);
		return Base64Util.encodeToString(enBytes);
	}
	/**
	 * RSA私钥加密
	 *
	 * @param bytes  加密字节数组
	 * @param priKey 私钥
	 * @return 密文字节
	 * @throws Exception 加密过程中的异常信息
	 */
	public static byte[] encryptByte(byte[] bytes, RSAPrivateKey priKey) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, priKey);
		byte[] enBytes = splitByteList(cipher, bytes, MAX_ENCRYPT_BLOCK);
		return Base64Util.encode(enBytes);
	}
	/**
	 * RSA公钥解密
	 *
	 * @param str    解密字符串
	 * @param pubKey 公钥
	 * @return 明文
	 * @throws Exception 解密过程中的异常信息
	 */
	public static Object decrypt(String str, RSAPublicKey pubKey) throws Exception {
		return decrypt(str.getBytes(StandardCharsets.UTF_8), pubKey);
	}
	/**
	 * RSA公钥解密
	 *
	 * @param bytes  解密字节数组
	 * @param pubKey 公钥
	 * @return 明文
	 * @throws Exception 解密过程中的异常信息
	 */
	public static Object decrypt(byte[] bytes, RSAPublicKey pubKey) throws Exception {
		byte[] inputByte = Base64Util.decode(bytes);
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, pubKey);
		byte[] deBytes = splitByteList(cipher, inputByte, MAX_DECRYPT_BLOCK);
		return ByteUtil.toObject(deBytes);
	}
	/**
	 * RSA公钥解密
	 *
	 * @param bytes  解密字节数组
	 * @param pubKey 公钥
	 * @return 明文字节
	 * @throws Exception 解密过程中的异常信息
	 */
	public static byte[] decryptByte(byte[] bytes, RSAPublicKey pubKey) throws Exception {
		byte[] inputByte = Base64Util.decode(bytes);
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, pubKey);
		byte[] deBytes = splitByteList(cipher, inputByte, MAX_DECRYPT_BLOCK);
		return deBytes;
	}
	// ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑ 私钥加密 and 公钥解密 ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

	/**
	 * 字节数据切割加密
	 *
	 * @param cipher   加密器
	 * @param bytes    字节数组
	 * @param maxBlock 每次加密长度
	 * @return 分组加密后的字节数组
	 * @throws BadPaddingException       错误填充异常
	 * @throws IllegalBlockSizeException 非法块大小异常
	 */
	public static byte[] splitByteList(
			Cipher cipher, byte[] bytes, int maxBlock
	) throws BadPaddingException, IllegalBlockSizeException {
		byte[] deBytes = null;
		final int length = bytes.length;
		if (length <= maxBlock) {
			return cipher.doFinal(bytes);
		}
		for (int i = 0; i < length; i += maxBlock) {
			byte[] doFinal = cipher.doFinal(ArrayUtils.subarray(bytes, i, i + maxBlock));
			deBytes = ArrayUtils.addAll(deBytes, doFinal);
		}
		return deBytes;
	}
}

