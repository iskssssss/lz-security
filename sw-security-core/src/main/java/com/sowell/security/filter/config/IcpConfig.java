package com.sowell.security.filter.config;

import com.sowell.security.IcpConstant;
import com.sowell.security.arrays.UrlHashSet;
import com.sowell.tool.core.ArraysUtil;
import com.sowell.tool.core.string.StringUtil;
import com.sowell.tool.encrypt.model.SwPrivateKey;
import com.sowell.tool.encrypt.model.SwPublicKey;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 配置文件
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @date 2021/08/26 11:27
 */
public class IcpConfig {

	/**
	 * 是否在控制台打印日志信息(默认打印)
	 */
	private Boolean consoleLogPrint = true;
	/**
	 * AccessToken 相关信息
	 */
	private AccessTokenConfig accessTokenConfig;
	/**
	 * 加密相关配置
	 */
	private EncryptConfig encryptConfig;
	/**
	 * 接口方法扫描位置
	 */
	private List<String> controllerMethodScanPathList;
	/**
	 * 功能类型:AUTH/FILTER（暂时无用）
	 */
	private String icpType = "FILTER";

	public Boolean getConsoleLogPrint() {
		return consoleLogPrint;
	}
	public void setConsoleLogPrint(Boolean consoleLogPrint) {
		this.consoleLogPrint = consoleLogPrint;
	}

	public AccessTokenConfig getAccessTokenConfig() {
		return accessTokenConfig;
	}
	public void setAccessTokenConfig(AccessTokenConfig accessTokenConfig) {
		this.accessTokenConfig = accessTokenConfig;
	}

	public EncryptConfig getEncryptConfig() {
		return encryptConfig;
	}
	public void setEncryptConfig(EncryptConfig encryptConfig) {
		this.encryptConfig = encryptConfig;
	}

	public List<String> getControllerMethodScanPathList() {
		return this.controllerMethodScanPathList;
	}
	public void setControllerMethodScanPathList(String controllerMethodScanPathList) {
		if (StringUtil.isEmpty(controllerMethodScanPathList)) {
			this.controllerMethodScanPathList = Collections.emptyList();
			return;
		}
		this.controllerMethodScanPathList = ArraysUtil.toList(StringUtil.delAllSpace(controllerMethodScanPathList), ",", true);
	}

	public String getIcpType() {
		return icpType;
	}
	public void setIcpType(String icpType) {
		this.icpType = icpType;
	}

	@Override
	public String toString() {
		return " 配置信息：" +
				"\n     token配置信息：" + accessTokenConfig +
				"\n     加密配置信息：" + encryptConfig +
				"\n     扫描位置：'" + controllerMethodScanPathList + '\'' +
				'}';
	}

	public static class AccessTokenConfig {
		/**
		 * accessToken 存放标识
		 */
		private String name = "Authorization";
		/**
		 * AccessToken默认过期时间(3600秒)
		 */
		private static final long DEFAULT_TIMEOUT = 3600L;
		/**
		 * AccessToken类型（uuid, jwt）
		 */
		private String accessTokenType = "UUID";
		/**
		 * AccessToken过期时间（秒）(默认3600秒)
		 */
		private Long timeout = 3600L;

		public String getName() {
			return this.name;
		}
		public void setName(String name) {
			this.name = name;
		}

		public String getAccessTokenType() {
			if (StringUtil.isEmpty(this.accessTokenType)) {
				return IcpConstant.ACCESS_TOKEN_TYPE_BY_UUID;
			}
			return this.accessTokenType.toUpperCase(Locale.ROOT);
		}
		public void setAccessTokenType(String accessTokenType) {
			this.accessTokenType = accessTokenType;
		}

		/**
		 * 获取过期时间（秒）
		 *
		 * @return 过期时间（秒）
		 */
		public long getTimeout() {
			final Long timeout = this.timeout;
			if (timeout == null) {
				return DEFAULT_TIMEOUT;
			}
			return timeout.intValue();
		}
		/**
		 * 获取过期时间（毫秒）
		 *
		 * @return 过期时间（毫秒）
		 */
		public long getTimeoutForMillis() {
			final long timeout = getTimeout();
			if (timeout == -1){
				return timeout;
			}
			return TimeUnit.SECONDS.toMillis(timeout);
		}
		public void setTimeout(Long timeout) {
			this.timeout = timeout;
		}
	}

	public static class EncryptConfig {
		/**
		 * 是否加密
		 */
		private Boolean encrypt = false;
		/**
		 * 公钥
		 */
		private SwPublicKey publicKeyStr;
		/**
		 * 私钥
		 */
		private SwPrivateKey privateKeyStr;

		/**
		 * 加密接口列表（逗号(,)隔开）
		 * <p>/a/**,/b 或 /a/**, /b</p>
		 */
		private UrlHashSet encryptUrlList;

		public Boolean getEncrypt() {
			return encrypt;
		}
		public void setEncrypt(Boolean encrypt) {
			this.encrypt = encrypt;
		}

		public SwPublicKey getPublicKeyStr() {
			return publicKeyStr;
		}
		public void setPublicKeyStr(String publicKeyStr) {
			if (StringUtil.isEmpty(publicKeyStr)) {
				return;
			}
			this.publicKeyStr = new SwPublicKey(publicKeyStr);
		}

		public SwPrivateKey getPrivateKeyStr() {
			return privateKeyStr;
		}
		public void setPrivateKeyStr(String privateKeyStr) {
			if (StringUtil.isEmpty(privateKeyStr)) {
				return;
			}
			this.privateKeyStr = new SwPrivateKey(privateKeyStr);
		}

		public UrlHashSet getEncryptUrlList() {
			if (this.encryptUrlList == null){
				this.encryptUrlList = UrlHashSet.empty();
			}
			return this.encryptUrlList;
		}
		public void setEncryptUrlList(String encryptUrlList) {
			if (StringUtil.isEmpty(encryptUrlList)) {
				this.encryptUrlList = UrlHashSet.empty();
				return;
			}
			this.encryptUrlList = new UrlHashSet(ArraysUtil.toList(StringUtil.delAllSpace(encryptUrlList), ",", true));
		}
	}
}
