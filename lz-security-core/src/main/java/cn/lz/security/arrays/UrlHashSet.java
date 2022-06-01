package cn.lz.security.arrays;

import cn.lz.security.LzCoreManager;
import cn.lz.security.context.LzContext;

import java.util.HashSet;
import java.util.List;

/**
 * @Version 版权 Copyright(c)2021 LZ
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
	public boolean containsPath(String url) {
		if (super.isEmpty()) {
			return false;
		}
		final boolean isContains = super.contains(url);
		if (isContains) {
			return true;
		}
		final LzContext<?, ?> lzContext = LzCoreManager.getLzContext();
		for (String value : this) {
			final String pattern = value.replace(" ", "");
			if (lzContext.matchUrl(pattern, url)) {
				return true;
			}
		}
		return false;
	}


}
