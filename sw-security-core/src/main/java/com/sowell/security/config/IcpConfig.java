package com.sowell.security.config;

import com.sowell.security.IcpConstant;
import com.sowell.security.arrays.UrlHashSet;
import com.sowell.tool.core.ArraysUtil;
import com.sowell.tool.core.string.StringUtil;
import com.sowell.tool.encrypt.model.SwPrivateKey;
import com.sowell.tool.encrypt.model.SwPublicKey;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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
	 * accessToken 在请求头中的键名
	 */
	private String headerName = "Authorization";
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
	 * 功能类型:AUTH/FILTER
	 */
	private String icpType = "FILTER";

	public Boolean getConsoleLogPrint() {
		return consoleLogPrint;
	}
	public void setConsoleLogPrint(Boolean consoleLogPrint) {
		this.consoleLogPrint = consoleLogPrint;
	}

	public String getHeaderName() {
		return headerName;
	}
	public void setHeaderName(String headerName) {
		this.headerName = headerName;
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
				"\n     头名称：" + headerName +
				"\n     token配置信息：" + accessTokenConfig +
				"\n     加密配置信息：" + encryptConfig +
				"\n     扫描位置：'" + controllerMethodScanPathList + '\'' +
				'}';
	}

	public static class AccessTokenConfig {
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
		private SwPublicKey publicKey;
		/**
		 * 私钥
		 */
		private SwPrivateKey privateKey;

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

		public SwPublicKey getPublicKey() {
			return publicKey;
		}
		public void setPublicKey(String publicKey) {
			if (StringUtil.isEmpty(publicKey)) {
				return;
			}
			this.publicKey = new SwPublicKey(publicKey);
		}

		public SwPrivateKey getPrivateKey() {
			return privateKey;
		}
		public void setPrivateKey(String privateKey) {
			if (StringUtil.isEmpty(privateKey)) {
				return;
			}
			this.privateKey = new SwPrivateKey(privateKey);
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
