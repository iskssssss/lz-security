package com.sowell.security.filter.context;

import com.sowell.security.filter.context.model.BaseRequest;
import com.sowell.security.filter.context.model.BaseResponse;
import com.sowell.security.filter.context.model.Box;
import com.sowell.security.filter.context.model.IcpStorage;

import java.io.IOException;
import java.util.Optional;

/**
 * @Version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/08/20 15:49
 */
public class IcpSecurityContextThreadLocal {

	private static final ThreadLocal<Box> BOX_THREAD_LOCAL = new InheritableThreadLocal<>();

	public static void setBox(
			BaseRequest<?> request,
			BaseResponse<?> response,
			IcpStorage<?> icpStorage
	) {
		BOX_THREAD_LOCAL.set(new Box(request, response, icpStorage));
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

	public static BaseRequest getServletRequest() {
		return getBox().getRequest();
	}

	public static BaseResponse getServletResponse() {
		return getBox().getResponse();
	}

	public static IcpStorage getIcpStorage() {
		return getBox().getIcpStorage();
	}
}
