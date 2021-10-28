package com.sowell.tool.encrypt.model;

import com.sowell.tool.encrypt.RSAEncryptUtil;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

/**
 * @Version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/10/14 11:02
 */
public class SwPrivateKey {

	private final String privateKeyStr;
	private final RSAPrivateKey privateKey;

	public SwPrivateKey(String privateKeyStr) {
		try {
			byte[] decoded = Base64.getDecoder().decode(privateKeyStr);
			this.privateKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
			this.privateKeyStr = privateKeyStr;
		} catch (InvalidKeySpecException | NoSuchAlgorithmException | IllegalArgumentException e) {
			throw new RuntimeException("无效私钥原文，请检查私钥原文。", e);
		}
	}
	public SwPrivateKey(RSAPrivateKey privateKey) {
		this.privateKey = privateKey;
		this.privateKeyStr = new String(Base64.getEncoder().encode((privateKey.getEncoded())));
	}
	public SwPrivateKey(String privateKeyStr, RSAPrivateKey privateKey) {
		this.privateKeyStr = privateKeyStr;
		this.privateKey = privateKey;
	}

	public String getPrivateKeyStr() {
		return privateKeyStr;
	}
	public RSAPrivateKey getPrivateKey() {
		return privateKey;
	}

	/**
	 * 私钥加密
	 *
	 * @param message 原文
	 * @return 密文
	 */
	public String encrypt(String message) {
		return encrypt(message.getBytes(StandardCharsets.UTF_8));
	}
	/**
	 * 私钥加密
	 *
	 * @param bytes 原文字节数组
	 * @return 密文
	 */
	public String encrypt(byte[] bytes) {
		try {
			return RSAEncryptUtil.encrypt(bytes, getPrivateKey());
		} catch (Exception e) {
			throw new RuntimeException("加密失败，请检查私钥或待加密信息。", e);
		}
	}

	/**
	 * 私钥解密
	 *
	 * @param message 密文
	 * @return 原文
	 */
	public Object decrypt(String message) {
		return decrypt(message.getBytes(StandardCharsets.UTF_8));
	}
	/**
	 * 私钥解密
	 *
	 * @param bytes 密文字节数组
	 * @return 原文
	 */
	public Object decrypt(byte[] bytes) {
		try {
			return RSAEncryptUtil.decrypt(bytes, getPrivateKey());
		} catch (Exception e) {
			throw new RuntimeException("解密失败，请检查私钥或待解密信息。", e);
		}
	}
}
