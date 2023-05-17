package cn.lz.security.context.model;

import java.io.Closeable;
import java.io.IOException;

/**
 * 存储器盒子
 * @Version 版权 Copyright(c)2021 LZ
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/10 17:29
 */
public class StorageBox<RequestType, ResponseType> implements Closeable {

	/**
	 * 请求流
	 */
	private final BaseResponse<ResponseType> response;

	/**
	 * 获取存储器
	 */
	private final Storage<RequestType> storage;

	public StorageBox(BaseResponse<ResponseType> response, Storage<RequestType> storage) {
		this.response = response;
		this.storage = storage;
	}

	/**
	 * 获取请求流
	 *
	 * @return 请求流
	 */
	public BaseRequest<RequestType> getRequest() {
		return this.storage.getRequest();
	}

	/**
	 * 获取响应流
	 *
	 * @return 响应流
	 */
	public BaseResponse<ResponseType> getResponse() {
		return response;
	}

	/**
	 * 获取存储器
	 *
	 * @return 存储器
	 */
	public Storage<?> getLzStorage() {
		return storage;
	}

	@Override
	public void close() throws IOException {
		this.storage.close();
	}
}
