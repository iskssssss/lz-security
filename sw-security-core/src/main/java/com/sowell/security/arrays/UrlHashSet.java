package com.sowell.security.arrays;

import com.sowell.security.IcpManager;
import com.sowell.security.context.IcpContext;

import java.util.HashSet;
import java.util.List;

/**
 * @Version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/22 14:02
 */
public class UrlHashSet extends HashSet<String> {

	public UrlHashSet() {
	}

	public UrlHashSet(List<String> encryptUrlList) {
		this.addAll(encryptUrlList);
	}

	public static UrlHashSet empty() {
		return new UrlHashSet();
	}

	/**
	 * 校验地址
	 *
	 * @param url 接口地址
	 * @return 是否匹配
	 */
	public boolean containsUrl(String url) {
		final boolean contains = super.contains(url);
		if (contains) {
			return true;
		}
		final IcpContext<?, ?> icpContext = IcpManager.getIcpContext();
		for (String value : this) {
			final String pattern = value.replace(" ", "");
			if (icpContext.matchUrl(pattern, url)) {
				return true;
			}
		}
		return false;
	}


}
