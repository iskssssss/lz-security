package com.sowell.tool.encrypt.model;

import com.sowell.tool.core.string.StringUtil;
import com.sowell.tool.encrypt.RSAEncryptUtil;

import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;

/**
 * @Version 版权 Copyright(c)2021 LZ
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/10/14 10:42
 */
public class SwKeyPair {

	/**
	 * 私钥
	 */
	SwPrivateKey privateKey;

	/**
	 * 公钥
	 */
	SwPublicKey publicKey;

	public SwKeyPair(String privateKeyStr, String publicKeyStr) {
		this.privateKey = new SwPrivateKey(privateKeyStr);
		this.publicKey = new SwPublicKey(publicKeyStr);
	}
	public SwKeyPair(RSAPrivateKey privateKey, RSAPublicKey publicKey) {
		this.privateKey = new SwPrivateKey(privateKey);
		this.publicKey = new SwPublicKey(publicKey);
	}

	public String getPrivateKeyStr() {
		final String privateKeyStr = this.privateKey.getPrivateKeyStr();
		if (!StringUtil.isEmpty(privateKeyStr)) {
			return privateKeyStr;
		}
		final RSAPrivateKey privateKey = this.privateKey.getPrivateKey();
		if (publicKey == null) {
			throw new RuntimeException("无私钥可用，请先设置私钥或这私钥原文。");
		}
		return new String(Base64.getEncoder().encode((privateKey.getEncoded())));
	}
	public SwPrivateKey getPrivateKey() {
		return privateKey;
	}

	public String getPublicKeyStr() {
		final String publicKeyStr = this.publicKey.getPublicKeyStr();
		if (!StringUtil.isEmpty(publicKeyStr)) {
			return publicKeyStr;
		}
		final RSAPublicKey publicKey = this.publicKey.getPublicKey();
		if (publicKey == null) {
			throw new RuntimeException("无公钥可用，请先设置公钥或这公钥原文。");
		}
		return new String(Base64.getEncoder().encode(publicKey.getEncoded()));
	}
	public SwPublicKey getPublicKey() {
		return publicKey;
	}

	/**
	 * 公钥加密
	 *
	 * @param message 原文
	 * @return 密文
	 */
	public String encryptByPublicKey(String message) {
		return publicKey.encrypt(message);
	}
	/**
	 * 公钥加密
	 *
	 * @param bytes 原文字节数组
	 * @return 密文
	 */
	public String encryptByPublicKey(byte[] bytes) {
		return publicKey.encrypt(bytes);
	}
	/**
	 * 公钥解密
	 *
	 * @param message 密文
	 * @return 原文
	 */
	public Object decryptByPublicKey(String message) {
		return publicKey.decrypt(message);
	}
	/**
	 * 公钥解密
	 *
	 * @param bytes 密文字节数组
	 * @return 原文
	 */
	public Object decryptByPublicKey(byte[] bytes) {
		return publicKey.decrypt(bytes);
	}

	/**
	 * 私钥加密
	 *
	 * @param message 原文
	 * @return 密文
	 */
	public String encryptByPrivateKey(String message) {
		return privateKey.encrypt(message);
	}
	/**
	 * 私钥加密
	 *
	 * @param bytes 原文字节数组
	 * @return 密文
	 */
	public String encryptByPrivateKey(byte[] bytes) {
		return privateKey.encrypt(bytes);
	}
	/**
	 * 私钥解密
	 *
	 * @param message 密文
	 * @return 原文
	 */
	public Object decryptByPrivateKey(String message) {
		return privateKey.decrypt(message);
	}
	/**
	 * 私钥解密
	 *
	 * @param bytes 密文字节数组
	 * @return 原文
	 */
	public Object decryptByPrivateKey(byte[] bytes) {
		return privateKey.decrypt(bytes);
	}

	public static SwKeyPair genKeyPair() {
		try {
			return RSAEncryptUtil.genKeyPair();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("生成密钥对失败。", e);
		}
	}
}
