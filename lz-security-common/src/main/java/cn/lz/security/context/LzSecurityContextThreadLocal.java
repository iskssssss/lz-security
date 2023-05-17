package cn.lz.security.context;

import cn.lz.security.context.model.BaseRequest;
import cn.lz.security.context.model.BaseResponse;
import cn.lz.security.context.model.StorageBox;
import cn.lz.security.context.model.Storage;

import java.io.IOException;

/**
 * @Version 版权 Copyright(c)2021 LZ
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/08/20 15:49
 */
public class LzSecurityContextThreadLocal {
	private static final ThreadLocal<StorageBox<?, ?>> BOX_THREAD_LOCAL = new InheritableThreadLocal<>();

	/**
	 * 设置存储器盒子
	 */
	public static void setStorageBox(BaseResponse<?> response, Storage<?> storage) {
		StorageBox<?, ?> value = new StorageBox(response, storage);
		BOX_THREAD_LOCAL.set(value);
	}

	/**
	 * 移除存储器盒子
	 */
	public static void remove() {
		final StorageBox<?, ?> storageBox = getStorageBox();
		try {
			if (storageBox == null) {
				return;
			}
			storageBox.close();
		} catch (IOException ignored) {
		} finally {
			BOX_THREAD_LOCAL.remove();
		}
	}

	/**
	 * 获取存储器盒子
	 *
	 * @return 存储器盒子
	 */
	private static StorageBox<?, ?> getStorageBox() {
		StorageBox<?, ?> storageBox = BOX_THREAD_LOCAL.get();
		return storageBox;
	}

	/**
	 * 获取请求流
	 *
	 * @return 请求流
	 */
	public static BaseRequest<?> getRequest() {
		final StorageBox<?, ?> storageBox = getStorageBox();
		if (storageBox == null) {
			return null;
		}
		return storageBox.getRequest();
	}

	/**
	 * 获取响应流
	 *
	 * @return 响应流
	 */
	public static BaseResponse<?> getResponse() {
		final StorageBox<?, ?> storageBox = getStorageBox();
		if (storageBox == null) {
			return null;
		}
		return storageBox.getResponse();
	}

	/**
	 * 获取存储器
	 *
	 * @return 存储器
	 */
	public static Storage<?> getLzStorage() {
		final StorageBox<?, ?> storageBox = getStorageBox();
		if (storageBox == null) {
			return null;
		}
		return storageBox.getLzStorage();
	}
}
