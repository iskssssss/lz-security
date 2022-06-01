package com.sowell.security.config;

import com.sowell.tool.core.ArraysUtil;
import com.sowell.tool.core.string.StringUtil;

import java.util.Collections;
import java.util.List;

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
		sb.append("\n     ").append("• 是否打印日志").append("：").append(consoleLogPrint);
		sb.append("\n     ").append("- Token配置信息").append("：").append(tokenConfig.toString());
		sb.append("\n     ").append("- 加密配置信息").append("：").append(encryptConfig.toString());
		sb.append("\n     ").append("• 扫描位置").append("：").append(this.getControllerMethodScanPathList());
		return sb.toString();
	}
}
