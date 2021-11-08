package com.sowell.security.defaults;

import com.sowell.security.IcpCoreManager;
import com.sowell.security.annotation.DataEncodeSwitch;
import com.sowell.security.arrays.UrlHashSet;
import com.sowell.security.config.IcpConfig;
import com.sowell.security.context.model.BaseRequest;
import com.sowell.security.handler.EncodeSwitchHandler;
import com.sowell.tool.http.enums.RequestMethodEnum;
import com.sowell.tool.reflect.model.ControllerMethod;

/**
 * TODO
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @date 2021/11/08 09:12
 */
public class DefaultEncodeSwitchHandler implements EncodeSwitchHandler {

	private final IcpConfig.EncryptConfig encryptConfig;

	public DefaultEncodeSwitchHandler() {
		encryptConfig = IcpCoreManager.getIcpConfig().getEncryptConfig();
	}

	@Override
	public boolean decrypt(
			BaseRequest<?> request
	) {
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
	public boolean encrypt(
			BaseRequest<?> request
	) {
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
}
