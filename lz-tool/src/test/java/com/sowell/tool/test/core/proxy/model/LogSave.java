package com.sowell.tool.test.core.proxy.model;

/**
 * TODO
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 LZ
 * @date 2021/11/01 09:58
 */
public class LogSave
		extends AbsLogSave
		implements ILogSave {

	private final boolean end;

	public LogSave() {
		this.end = false;
	}

	public LogSave(boolean end) {this.end = end;}

	@Override
	public boolean save() {
		System.out.println("log save");
		System.out.println(end);
		return true;
	}

	@Override
	public void delete() {
		System.out.println("log delete");
	}
}

