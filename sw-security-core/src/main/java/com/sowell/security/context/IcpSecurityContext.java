package com.sowell.security.context;

import com.sowell.security.context.model.Box;
import com.sowell.security.context.model.IcpAbstractStorage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * @Version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/08/20 15:49
 */
public class IcpSecurityContext {

	private static final ThreadLocal<Box> BOX_THREAD_LOCAL = new InheritableThreadLocal<>();

	public static void setBox(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,
			IcpAbstractStorage icpAbstractStorage
	) {
		BOX_THREAD_LOCAL.set(new Box(httpServletRequest, httpServletResponse, icpAbstractStorage));
	}

	public static void remove() {
		final Box box = getBox();
		try {
			box.close();
		} catch (IOException ignored) {
		}
		BOX_THREAD_LOCAL.remove();
	}

	private static Box getBox() {
		final Optional<Box> box = Optional.ofNullable(BOX_THREAD_LOCAL.get());
		return box.orElse(new Box());
	}

	public static HttpServletRequest getServletRequest() {
		return getBox().getRequest();
	}

	public static HttpServletResponse getServletResponse() {
		return getBox().getResponse();
	}

	public static IcpAbstractStorage getIcpAbstractStorage() {
		return getBox().getIcpAbstractStorage();
	}
}
