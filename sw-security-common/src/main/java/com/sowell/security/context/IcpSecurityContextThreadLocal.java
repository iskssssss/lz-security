package com.sowell.security.context;

import com.sowell.security.context.model.BaseRequest;
import com.sowell.security.context.model.BaseResponse;
import com.sowell.security.context.model.Box;
import com.sowell.security.context.model.IcpStorage;

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
			if (box == null) {
				return;
			}
			box.close();
		} catch (IOException ignored) {
		} finally {
			BOX_THREAD_LOCAL.remove();
		}
	}

	private static Box getBox() {
		Box box = BOX_THREAD_LOCAL.get();
		return box;
	}

	public static BaseRequest getServletRequest() {
		final Box box = getBox();
		if (box == null) {
			return null;
		}
		return box.getRequest();
	}

	public static BaseResponse getServletResponse() {
		final Box box = getBox();
		if (box == null) {
			return null;
		}
		return box.getResponse();
	}

	public static IcpStorage getIcpStorage() {
		final Box box = getBox();
		if (box == null) {
			return null;
		}
		return box.getIcpStorage();
	}
}
