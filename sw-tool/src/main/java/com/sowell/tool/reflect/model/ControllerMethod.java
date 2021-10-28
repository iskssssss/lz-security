package com.sowell.tool.reflect.model;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * TODO
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @date 2021/10/27 10:47
 */
public class ControllerMethod {
	private final Class<?> controllerClass;

	private final Method method;

	public ControllerMethod(Class<?> controllerClass, Method method) {
		this.controllerClass = controllerClass;
		this.method = method;
	}

	public Class<?> getControllerClass() {
		return controllerClass;
	}

	public Method getMethod() {
		return method;
	}

	public <T extends Annotation> boolean isMethodAndControllerAnnotation(Class<T> annotationClass) {
		return getMethodAndControllerAnnotation(annotationClass) != null;
	}

	public <T extends Annotation> T getMethodAndControllerAnnotation(Class<T> annotationClass) {
		final T methodAnnotation = getMethodAnnotation(annotationClass);
		if (methodAnnotation != null) {
			return methodAnnotation;
		}
		return getControllerAnnotation(annotationClass);
	}

	public <T extends Annotation> boolean isMethodAnnotation(Class<T> annotationClass) {
		return getMethodAnnotation(annotationClass) != null;
	}

	public <T extends Annotation> T getMethodAnnotation(Class<T> annotationClass) {
		return this.method.getAnnotation(annotationClass);
	}

	public <T extends Annotation> boolean isControllerAnnotation(Class<T> annotationClass) {
		return getControllerAnnotation(annotationClass) != null;
	}

	public <T extends Annotation> T getControllerAnnotation(Class<T> annotationClass) {
		return this.controllerClass.getAnnotation(annotationClass);
	}

	@Override
	public String toString() {
		return "ControllerMethod{" +
				"controllerClass=" + controllerClass +
				", method=" + method +
				'}';
	}
}
