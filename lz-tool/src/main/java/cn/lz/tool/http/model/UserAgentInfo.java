package cn.lz.tool.http.model;

import cn.lz.tool.core.model.BaseModel;

/**
 * @Version 版权 Copyright(c)2021 LZ
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/10 14:19
 */
public class UserAgentInfo extends BaseModel {
	/**
	 * ua
	 */
	private String ua;
	/**
	 * 客户端ip
	 */
	private String ipAddr;
	/**
	 * ip地址所在地区
	 */
	private String ipAddrName;
	/**
	 * 浏览器
	 */
	private String browser;
	/**
	 * 平台/操作系统
	 */
	private String platform;
	/**
	 * 1 手机端 0 桌面端
	 */
	private Integer isMobile;
	/**
	 * 浏览器版本
	 */
	private String browserVersion;
	/**
	 * 操作系统及版本
	 */
	private String os;

	public String getUa() {
		return ua;
	}

	public void setUa(String ua) {
		this.ua = ua;
	}

	public String getIpAddr() {
		return ipAddr;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	public String getIpAddrName() {
		return ipAddrName;
	}

	public void setIpAddrName(String ipAddrName) {
		this.ipAddrName = ipAddrName;
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public Integer getIsMobile() {
		return isMobile;
	}

	public void setIsMobile(Integer isMobile) {
		this.isMobile = isMobile;
	}

	public String getBrowserVersion() {
		return browserVersion;
	}

	public void setBrowserVersion(String browserVersion) {
		this.browserVersion = browserVersion;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}
}
