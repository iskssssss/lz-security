package cn.lz.security.filter;

import cn.lz.security.annotation.LogBeforeFilter;
import cn.lz.security.exception.base.SecurityException;
import cn.lz.security.filter.context.SpringBootContextTheadLocal;
import cn.lz.security.filter.mode.LzRequest;
import cn.lz.security.filter.mode.LzResponse;
import cn.lz.security.filter.utils.SpringUtil;
import cn.lz.security.log.LzLogger;
import cn.lz.security.log.LzLoggerUtil;
import cn.lz.tool.core.enums.HttpStatus;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

/**
 * 接口过滤处理中心
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 LZ
 * @date 2021/11/03 11:22
 */
public class LzInterfaceFilterCore extends BaseFilterCore {
	protected final static LzLogger logger = LzLoggerUtil.getLzLogger(LzInterfaceFilterCore.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.info("接口过滤处理中心 - init");
		if (SpringUtil.getBeansWithAnnotationCount(LogBeforeFilter.class) > 1) {
			throw new RuntimeException("注解(LogBeforeFilter)全局只可存在一个。");
		}
		LzFilterManager.init();
	}

	@Override
	public boolean doFilter(LzRequest lzRequest, LzResponse lzResponse, SpringBootContextTheadLocal context) {
		try {
			return LzFilterManager.filter();
		} catch (Exception e) {
			if (e instanceof SecurityException) {
				throw e;
			} else if (e.getCause() instanceof SecurityException) {
				throw (SecurityException) e.getCause();
			} else {
				throw new SecurityException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "过滤异常", e);
			}
		}
	}

	@Override
	public void destroy() {
		try {
			logger.info("接口过滤处理中心 - destroy");
			LzFilterManager.destroy();
			super.destroy();
		} finally {
			// TODO
		}
	}
}
