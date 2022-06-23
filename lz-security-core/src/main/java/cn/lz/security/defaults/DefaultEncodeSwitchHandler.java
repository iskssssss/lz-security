package cn.lz.security.defaults;

import cn.lz.security.LzCoreManager;
import cn.lz.security.annotation.DataEncodeSwitch;
import cn.lz.security.arrays.UrlHashSet;
import cn.lz.security.config.EncryptConfig;
import cn.lz.security.context.model.BaseRequest;
import cn.lz.security.handler.EncodeSwitchHandler;
import cn.lz.tool.http.enums.MediaType;
import cn.lz.tool.http.enums.RequestMethodEnum;
import cn.lz.tool.reflect.model.ControllerMethod;

/**
 * TODO
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 LZ
 * @date 2021/11/08 09:12
 */
public class DefaultEncodeSwitchHandler implements EncodeSwitchHandler {

	private final EncryptConfig encryptConfig;

	public DefaultEncodeSwitchHandler() {
		encryptConfig = LzCoreManager.getLzConfig().getEncryptConfig();
	}

	@Override
	public boolean isDecrypt(BaseRequest<?> request) {
		if (!encryptConfig.getEncrypt() || RequestMethodEnum.POST.not(request.getMethod())) {
			return false;
		}
		DataEncodeSwitch dataEncodeSwitch = null;
		final ControllerMethod controllerMethod = request.getControllerMethod();
		if (controllerMethod != null) {
			// 获取当前访问接口方法/类的加密注解
			dataEncodeSwitch = controllerMethod.getMethodAndControllerAnnotation(DataEncodeSwitch.class);
		}

		if (dataEncodeSwitch == null) {
			final UrlHashSet encryptUrlList = encryptConfig.getEncryptUrlList();
			return encryptUrlList.containsPath(request.getRequestPath());
		}
		return dataEncodeSwitch.requestEncrypt();
	}

	@Override
	public boolean isEncrypt(BaseRequest<?> request) {
		if (!encryptConfig.getEncrypt()) {
			return false;
		}
		DataEncodeSwitch dataEncodeSwitch = null;
		final ControllerMethod controllerMethod = request.getControllerMethod();
		if (controllerMethod != null) {
			// 获取当前访问接口方法/类的加密注解
			dataEncodeSwitch = controllerMethod.getMethodAndControllerAnnotation(DataEncodeSwitch.class);
		}
		if (dataEncodeSwitch == null) {
			final UrlHashSet encryptUrlList = encryptConfig.getEncryptUrlList();
			return encryptUrlList.containsPath(request.getRequestPath());
		} else {
			return dataEncodeSwitch.responseEncrypt();
		}
	}

	@Override
	public String responseContentType(BaseRequest<?> request) {
		DataEncodeSwitch dataEncodeSwitch = null;
		final ControllerMethod controllerMethod = request.getControllerMethod();
		if (controllerMethod != null) {
			// 获取当前访问接口方法/类的加密注解
			dataEncodeSwitch = controllerMethod.getMethodAndControllerAnnotation(DataEncodeSwitch.class);
		}
		if (dataEncodeSwitch == null) {
			return MediaType.APPLICATION_JSON_VALUE;
		}
		return dataEncodeSwitch.responseContentType();
	}

	@Override
	public String requestContentType(BaseRequest<?> request) {
		DataEncodeSwitch dataEncodeSwitch = null;
		final ControllerMethod controllerMethod = request.getControllerMethod();
		if (controllerMethod != null) {
			// 获取当前访问接口方法/类的加密注解
			dataEncodeSwitch = controllerMethod.getMethodAndControllerAnnotation(DataEncodeSwitch.class);
		}
		if (dataEncodeSwitch == null) {
			return MediaType.APPLICATION_JSON_VALUE;
		}
		return dataEncodeSwitch.requestContentType();
	}
}
