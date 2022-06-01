package com.sowell.tool.encrypt.model;

import com.sowell.tool.encrypt.RSAEncryptUtil;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * @Version 版权 Copyright(c)2021 LZ
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/10/14 11:02
 */
public class SwPublicKey {

	private final String publicKeyStr;
	private final RSAPublicKey publicKey;

	public SwPublicKey(String publicKeyStr) {
		try {
			byte[] decoded = Base64.getDecoder().decode(publicKeyStr);
			this.publicKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
			this.publicKeyStr = publicKeyStr;
		} catch (InvalidKeySpecException | NoSuchAlgorithmException | IllegalArgumentException e) {
			throw new RuntimeException("无效公钥原文，请检查公钥原文。", e);
		}
	}
	public SwPublicKey(RSAPublicKey publicKey) {
		this.publicKey = publicKey;
		this.publicKeyStr = new String(Base64.getEncoder().encode(publicKey.getEncoded()));
	}
	public SwPublicKey(String publicKeyStr, RSAPublicKey publicKey) {
		this.publicKeyStr = publicKeyStr;
		this.publicKey = publicKey;
	}

	public String getPublicKeyStr() {
		return publicKeyStr;
	}
	public RSAPublicKey getPublicKey() {
		return publicKey;
	}

	/**
	 * 公钥加密
	 *
	 * @param message 原文
	 * @return 密文
	 */
	public String encrypt(String message) {
		return encrypt(message.getBytes(StandardCharsets.UTF_8));
	}
	/**
	 * 公钥加密
	 *
	 * @param bytes 原文字节数组
	 * @return 密文
	 */
	public String encrypt(byte[] bytes) {
		try {
			return RSAEncryptUtil.encrypt(bytes, getPublicKey());
		} catch (Exception e) {
			throw new RuntimeException("加密失败，请检查公钥或待加密信息。", e);
		}
	}

	/**
	 * 公钥解密
	 *
	 * @param message 密文
	 * @return 原文
	 */
	public Object decrypt(String message) {
		return decrypt(message.getBytes(StandardCharsets.UTF_8));
	}
	/**
	 * 公钥解密
	 *
	 * @param bytes 密文字节数组
	 * @return 原文
	 */
	public Object decrypt(byte[] bytes) {
		try {
			return RSAEncryptUtil.decrypt(bytes, getPublicKey());
		} catch (Exception e) {
			throw new RuntimeException("解密失败，请检查公钥或待解密信息。", e);
		}
	}
}
