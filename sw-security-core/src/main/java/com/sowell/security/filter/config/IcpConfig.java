package com.sowell.security.filter.config;

import com.sowell.security.IcpConstant;
import com.sowell.security.arrays.UrlHashSet;
import com.sowell.tool.core.ArraysUtil;
import com.sowell.tool.core.string.StringUtil;
import com.sowell.tool.encrypt.model.SwPrivateKey;
import com.sowell.tool.encrypt.model.SwPublicKey;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
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
	 * Token配置信息
	 */
	private TokenConfig tokenConfig;
	/**
	 * 加密配置信息
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

	/**
	 * 获取是否打印日志
	 *
	 * @return 是否打印
	 */
	public Boolean getConsoleLogPrint() {
		return consoleLogPrint;
	}

	/**
	 * 设置是否打印日志
	 *
	 * @param consoleLogPrint 是否打印
	 */
	public void setConsoleLogPrint(Boolean consoleLogPrint) {
		this.consoleLogPrint = consoleLogPrint;
	}

	/**
	 * 获取Token配置信息
	 *
	 * @return Token配置信息
	 */
	public TokenConfig getTokenConfig() {
		return tokenConfig;
	}

	/**
	 * 设置Token配置信息
	 *
	 * @param tokenConfig Token配置信息
	 */
	public void setTokenConfig(TokenConfig tokenConfig) {
		this.tokenConfig = tokenConfig;
	}

	/**
	 * 获取加解密配置信息
	 *
	 * @return 加解密配置信息
	 */
	public EncryptConfig getEncryptConfig() {
		return encryptConfig;
	}

	/**
	 * 获取加解密配置信息
	 *
	 * @param encryptConfig 加解密配置信息
	 */
	public void setEncryptConfig(EncryptConfig encryptConfig) {
		this.encryptConfig = encryptConfig;
	}

	/**
	 * 获取接口扫描路径列表
	 *
	 * @return 接口扫描路径列表
	 */
	public List<String> getControllerMethodScanPathList() {
		if (StringUtil.isNull(this.controllerMethodScanPathList)) {
			this.controllerMethodScanPathList = Collections.emptyList();
		}
		return this.controllerMethodScanPathList;
	}

	/**
	 * 设置接口扫描路径
	 *
	 * @param controllerMethodScanPathList 接口扫描路径
	 */
	public void setControllerMethodScanPathList(String controllerMethodScanPathList) {
		if (StringUtil.isEmpty(controllerMethodScanPathList)) {
			this.controllerMethodScanPathList = Collections.emptyList();
			return;
		}
		this.controllerMethodScanPathList = ArraysUtil.toList(StringUtil.delAllSpace(controllerMethodScanPathList), ",", true);
	}

	/**
	 * 获取启用类型
	 *
	 * @return 启用类型
	 */
	public String getIcpType() {
		return icpType;
	}

	/**
	 * 设置类型
	 *
	 * @param icpType 类型
	 */
	public void setIcpType(String icpType) {
		this.icpType = icpType;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("配置信息：");
		sb.append("\n     ").append("TOKEN配置信息").append("：").append(tokenConfig.toString());
		sb.append("\n     ").append("加密配置信息").append("：").append(encryptConfig.toString());
		sb.append("\n     ").append("扫描位置").append("：").append(this.getControllerMethodScanPathList());
		return sb.toString();
	}

	public static class TokenConfig {
		/**
		 * Token 存放标识
		 */
		private String name = "Authorization";
		/**
		 * Token 默认过期时间(3600秒)
		 */
		private static final long DEFAULT_TIMEOUT = 3600L;
		/**
		 * Token 类型（uuid, jwt）
		 */
		private String type = "UUID";
		/**
		 * Token 过期时间（秒）(默认3600秒)
		 */
		private Long timeout = 3600L;

		public String getName() {
			return this.name;
		}

		/**
		 * 设置Token存放标识
		 *
		 * @param name 标识
		 */
		public void setName(String name) {
			this.name = name;
		}

		/**
		 * 获取类型
		 *
		 * @return 类型
		 */
		public String getType() {
			if (StringUtil.isEmpty(this.type)) {
				return IcpConstant.TOKEN_TYPE_BY_UUID;
			}
			return this.type.toUpperCase(Locale.ROOT);
		}

		/**
		 * 设置类型
		 *
		 * @param type 类型
		 */
		public void setType(String type) {
			this.type = type;
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
			if (timeout == -1) {
				return timeout;
			}
			return TimeUnit.SECONDS.toMillis(timeout);
		}

		/**
		 * 设置过期时间（秒）
		 *
		 * @param timeout 过期时间（秒）
		 */
		public void setTimeout(Long timeout) {
			this.timeout = timeout;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append("\n          ").append("Token 存放标识").append("：").append(name);
			sb.append("\n          ").append("Token 类型").append("：").append(type);
			sb.append("\n          ").append("Token 过期时间（秒）").append("：").append(timeout);
			return sb.toString();
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

		/**
		 * 获取是否加密
		 *
		 * @return 是否加密
		 */
		public Boolean getEncrypt() {
			return encrypt;
		}

		/**
		 * 设置是否加密
		 *
		 * @param encrypt 是否加密
		 */
		public void setEncrypt(Boolean encrypt) {
			this.encrypt = encrypt;
		}

		/**
		 * 获取公钥
		 *
		 * @return 公钥
		 */
		public SwPublicKey getPublicKeyStr() {
			return publicKeyStr;
		}

		/**
		 * 设置公钥
		 *
		 * @param publicKeyStr 公钥
		 */
		public void setPublicKeyStr(String publicKeyStr) {
			if (StringUtil.isEmpty(publicKeyStr)) {
				return;
			}
			this.publicKeyStr = new SwPublicKey(publicKeyStr);
		}

		/**
		 * 获取私钥
		 *
		 * @return 私钥
		 */
		public SwPrivateKey getPrivateKeyStr() {
			return privateKeyStr;
		}

		/**
		 * 设置私钥
		 *
		 * @param privateKeyStr 私钥
		 */
		public void setPrivateKeyStr(String privateKeyStr) {
			if (StringUtil.isEmpty(privateKeyStr)) {
				return;
			}
			this.privateKeyStr = new SwPrivateKey(privateKeyStr);
		}

		/**
		 * 获取加密接口列表
		 *
		 * @return 加密接口列表
		 */
		public UrlHashSet getEncryptUrlList() {
			if (this.encryptUrlList == null) {
				this.encryptUrlList = UrlHashSet.empty();
			}
			return this.encryptUrlList;
		}

		/**
		 * 设置加密接口列表
		 *
		 * @param encryptUrlList 加密接口列表
		 */
		public void setEncryptUrlList(String encryptUrlList) {
			if (StringUtil.isEmpty(encryptUrlList)) {
				this.encryptUrlList = UrlHashSet.empty();
				return;
			}
			this.encryptUrlList = new UrlHashSet(ArraysUtil.toList(StringUtil.delAllSpace(encryptUrlList), ",", true));
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append("\n          ").append("是否加密").append("：").append(encrypt);
			sb.append("\n          ").append("私钥").append("：").append(privateKeyStr.getPrivateKeyStr());
			sb.append("\n          ").append("公钥").append("：").append(publicKeyStr.getPublicKeyStr());
			sb.append("\n          ").append("加密接口列表").append("：").append(this.getEncryptUrlList().toString());
			return sb.toString();
		}
	}
}
