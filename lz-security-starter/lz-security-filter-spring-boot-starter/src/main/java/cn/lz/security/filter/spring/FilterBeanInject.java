package cn.lz.security.filter.spring;

import cn.lz.security.filter.LzFilterManager;
import cn.lz.security.handler.BaseFilterErrorHandler;
import cn.lz.security.log.BaseFilterLogHandler;
import cn.lz.security.tool.utils.SpringUtil;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 自动注入类
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 LZ
 * @date 2021/11/03 10:42
 */
public class FilterBeanInject extends LzFilterManager {

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
