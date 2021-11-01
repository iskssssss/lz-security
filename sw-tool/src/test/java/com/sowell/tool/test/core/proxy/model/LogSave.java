package com.sowell.tool.test.core.proxy.model;

/**
 * TODO
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @date 2021/11/01 09:58
 */
public class LogSave extends ILogSave {

	@Override
	public boolean save() {
		System.out.println("log save");
		return true;
	}
}

