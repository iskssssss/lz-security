package cn.lz.security.context;

import cn.lz.security.context.model.BaseRequest;
import cn.lz.security.context.model.BaseResponse;
import cn.lz.security.context.model.Box;
import cn.lz.security.context.model.LzStorage;

import java.io.IOException;

/**
 * @Version 版权 Copyright(c)2021 LZ
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/08/20 15:49
 */
public class LzSecurityContextThreadLocal {

	private static final ThreadLocal<Box> BOX_THREAD_LOCAL = new InheritableThreadLocal<>();

	public static void setBox(
			BaseRequest<?> request,
			BaseResponse<?> response,
			LzStorage<?> lzStorage
	) {
		BOX_THREAD_LOCAL.set(new Box(request, response, lzStorage));
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

	public static LzStorage getLzStorage() {
		final Box box = getBox();
		if (box == null) {
			return null;
		}
		return box.getLzStorage();
	}
}