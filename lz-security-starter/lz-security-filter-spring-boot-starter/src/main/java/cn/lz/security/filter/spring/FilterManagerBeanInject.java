package cn.lz.security.filter.spring;

import cn.lz.security.filter.LzFilterManager;
import cn.lz.security.handler.BaseFilterErrorHandler;
import cn.lz.security.log.BaseFilterLogHandler;
import cn.lz.security.tool.utils.SpringUtil;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Version 版权 Copyright(c)2021 LZ
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/17 15:16
 */
public class FilterManagerBeanInject extends LzFilterManager {

	/**
	 * 自动注入<b>过滤日志处理器</b>
	 */
	@Autowired(required = false)
	public void injectFilterLogHandler(BaseFilterLogHandler filterLogHandler) {
		if (LzFilterManager.filterLogHandler != null) {
			SpringUtil.destroyBean(filterLogHandler);
			return;
		}
		LzFilterManager.setFilterLogHandler(filterLogHandler);
	}

	/**
	 * 自动注入<b>过滤错误处理器</b>
	 */
	@Autowired(required = false)
	public void injectFilterErrorHandler(BaseFilterErrorHandler<?> filterErrorHandler) {
		if (LzFilterManager.filterErrorHandler != null) {
			SpringUtil.destroyBean(filterErrorHandler);
			return;
		}
		LzFilterManager.setFilterErrorHandler(filterErrorHandler);
	}
}
