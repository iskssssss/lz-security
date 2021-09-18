package com.sowell.security.utils;

import cn.hutool.core.io.FileTypeUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Version 版权 Copyright(c)2021 浙江设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/6/20 0:57
 */
public class SpringUtil implements BeanFactoryPostProcessor {
    /**
     * spring应用上下文
     */
    private static ConfigurableListableBeanFactory beanFactory;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        SpringUtil.beanFactory = configurableListableBeanFactory;
    }

    /**
     * 获取类型对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) throws BeansException {
        return (T) SpringUtil.beanFactory.getBean(name);
    }

    /**
     * 获取类型为requiredType的对象
     */
    public static <T> T getBean(Class<T> requiredType) throws BeansException {
        return SpringUtil.beanFactory.getBean(requiredType);
    }

    public static <T> List<T> getBeanListBySuperClass(Class<? extends Annotation> aClass, Class<T> superClass) {
        final Map<String, Object> beansWithAnnotation = SpringUtil.beanFactory.getBeansWithAnnotation(aClass);
        final List<T> list = beansWithAnnotation.values()
                .stream()
                .filter(item -> item.getClass().getSuperclass().equals(superClass))
                .map(item -> (T) item)
                .collect(Collectors.toList());
        return list;
    }

    public static Map<String, Object> getBeansWithAnnotation(
            Class<? extends Annotation> superClass
    ) {
        return beanFactory.getBeansWithAnnotation(superClass);
    }

    public static Map<String, Object> getBeansWithAnnotationByPackage(
            Class<? extends Annotation> superClass,
            Set<String> packagePaths
    ) {
        final Map<String, Object> beansWithAnnotation = getBeansWithAnnotation(superClass);
        final Iterator<String> iterator = beansWithAnnotation.keySet().iterator();
        while (iterator.hasNext()) {
            final String key = iterator.next();
            final Object controller = beansWithAnnotation.get(key);
            if (!isPackage(controller, packagePaths)) {
                iterator.remove();
            }
        }
        return beansWithAnnotation;
    }

    /**
     * 判断对象是否是 #{packagePaths} 下的
     *
     * @param controller   对象
     * @param packagePaths 包名列表
     * @return 结果
     */
    public static boolean isPackage(
            Object controller,
            Set<String> packagePaths
    ) {
        if (packagePaths == null || packagePaths.isEmpty()) {
            return true;
        }
        final Class<?> aClass = controller.getClass();
        final Package aPackage = aClass.getPackage();
        final String name = aPackage.getName();
        for (String packagePath : packagePaths) {
            if (name.startsWith(packagePath)) {
                return true;
            }
        }
        return false;
    }

    public static Map<String, Method> initControllerMethodMap(Set<String> packagePaths) {
        // 接口对应方法
        final Map<String, Method> interfacesMethodMap = new HashMap<>(16);
        // 获取所有控制器
        final Map<String, Object> restControllerBeanMap = SpringUtil.getBeansWithAnnotationByPackage(RestController.class, packagePaths);
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



    /**
     * 获取文件后缀
     *
     * @param file 文件
     * @return 文件后缀
     * @throws IOException 流异常
     */
    public static String getFileType(MultipartFile file) throws IOException {
        final String originalFilename = file.getOriginalFilename();
        String fileType = FileTypeUtil.getType(file.getInputStream());
        if (StringUtil.isNotEmpty(fileType)) {
            return fileType;
        }
        if (StringUtil.isEmpty(originalFilename)) {
            return null;
        }
        final String[] fileNameSplit = originalFilename.split("\\.");
        if (fileNameSplit.length < 2) {
            return null;
        }
        return fileNameSplit[fileNameSplit.length - 1];
    }
}
