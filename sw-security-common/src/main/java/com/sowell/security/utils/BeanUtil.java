package com.sowell.security.utils;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @Version 版权 Copyright(c)2021 浙江设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/7/1 9:50
 */
public class BeanUtil extends cn.hutool.core.bean.BeanUtil {

    public static Map<String, Method> initControllerMethodMap() {
        // 接口对应方法
        final Map<String, Method> interfacesMethodMap = new HashMap<>(16);
        // 获取所有控制器
        final Map<String, Object> restControllerBeanMap = SpringUtil.getBeansWithAnnotationByPackage(RestController.class, "com.sowell.iop.icp");
        // 获取控制器中的方法
        for (Object restControllerBean : restControllerBeanMap.values()) {
            final Class<?> restControllerBeanClass = restControllerBean.getClass();
            final List<String> controllerPathList = getMappingPaths(restControllerBeanClass);
            ReflectionUtils.doWithMethods(restControllerBeanClass, method -> {
                final List<String> methodPathList = getMappingPaths(method);
                if (methodPathList == null) {
                    return;
                }
                // 拼接接口
                if (controllerPathList == null) {
                    for (String methodPath : methodPathList) {
                        interfacesMethodMap.put(methodPath, method);
                    }
                    return;
                }
                for (String controllerPath : controllerPathList) {
                    for (String methodPath : methodPathList) {
                        interfacesMethodMap.put(controllerPath + methodPath, method);
                    }
                }
            }, ReflectionUtils.USER_DECLARED_METHODS);
        }
        return interfacesMethodMap;
    }

    public static Map<String, Map<String, String>> getControllerInterfaceMap() {
        Map<String, Map<String, String>> controllerInterfaceMap = new HashMap<>();
        // 获取所有控制器
        final Map<String, Object> beansWithAnnotation = SpringUtil.getBeansWithAnnotationByPackage(RestController.class, "com.sowell.iop.icp");
        // 获取控制器中的方法
        for (Object value : beansWithAnnotation.values()) {
            final Class<?> aClass = value.getClass();
            // 获取swagger注解信息
            Api classAnno = aClass.getAnnotation(Api.class);
            if (classAnno == null) {
                continue;
            }
            final Map<String, String> methodInterfaceMap = controllerInterfaceMap.computeIfAbsent(classAnno.tags()[0], t -> new HashMap<>(8));
            // 获取controller层接口信息
            final List<String> controllerPathList = getMappingPaths(aClass);
            ReflectionUtils.doWithMethods(aClass, method -> {
                // 获取method层接口信息
                final List<String> methodPathList = getMappingPaths(method);
                // 获取swagger注解信息
                ApiOperation methodAnno = method.getAnnotation(ApiOperation.class);
                if (methodAnno == null || methodPathList == null) {
                    return;
                }
                final String value1 = methodAnno.value();
                // 拼接接口
                if (controllerPathList == null) {
                    for (String methodPath : methodPathList) {
                        methodInterfaceMap.put(methodPath, value1);
                    }
                    return;
                }
                for (String controllerPath : controllerPathList) {
                    for (String methodPath : methodPathList) {
                        methodInterfaceMap.put(controllerPath + methodPath, value1);
                    }
                }
            }, ReflectionUtils.USER_DECLARED_METHODS);
        }
        return controllerInterfaceMap;
    }

    /**
     * 获取注解信息
     */
    public static List<String> getMappingPaths(AnnotatedElement annotatedElement) {
        final RequestMapping requestMapping = annotatedElement.getAnnotation(RequestMapping.class);
        String[] pathList = null;
        if (null != requestMapping) {
            pathList = requestMapping.value().length > 0 ? requestMapping.value() : requestMapping.path();
        }
        final GetMapping getMapping = annotatedElement.getAnnotation(GetMapping.class);
        if (null != getMapping) {
            pathList = getMapping.value().length > 0 ? getMapping.value() : getMapping.path();
        }
        final PostMapping postMapping = annotatedElement.getAnnotation(PostMapping.class);
        if (null != postMapping) {
            pathList = postMapping.value().length > 0 ? postMapping.value() : postMapping.path();
        }
        final PutMapping putMapping = annotatedElement.getAnnotation(PutMapping.class);
        if (null != putMapping) {
            pathList = putMapping.value().length > 0 ? putMapping.value() : putMapping.path();
        }
        final DeleteMapping deleteMapping = annotatedElement.getAnnotation(DeleteMapping.class);
        if (null != deleteMapping) {
            pathList = deleteMapping.value().length > 0 ? deleteMapping.value() : deleteMapping.path();
        }
        if (pathList == null || pathList.length < 1) {
            return null;
        }
        return Arrays.asList(pathList);
    }

    public static <T> T toBean(String jsonStr, Class<T> tClass) {
        final Map<String, Object> map = JSONObject.parseObject(jsonStr, Map.class);
        Set<Field> fieldList = new HashSet<>();
        ObjectUtil.getFieldList(fieldList, tClass);
        final T t = ObjectUtil.newInstance(tClass);
        for (Field declaredField : fieldList) {
            final String name = declaredField.getName();
            if (!map.containsKey(name)) {
                continue;
            }
            declaredField.setAccessible(true);
            final Object o = map.get(name);
            try {
                declaredField.set(t, o);
            } catch (IllegalAccessException ignored) {
            }
        }
        return t;
    }
}
