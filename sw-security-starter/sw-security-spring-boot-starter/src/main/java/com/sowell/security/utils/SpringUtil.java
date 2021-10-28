package com.sowell.security.utils;

import cn.hutool.core.io.FileTypeUtil;
import com.sowell.security.annotation.LogBeforeFilter;
import com.sowell.tool.core.string.StringUtil;
import com.sowell.tool.reflect.model.ControllerMethod;
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
@SuppressWarnings("unchecked")
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
     * 根据名称获取类型对象
     *
     * @param name 名称
     * @param <T>  返回类型
     * @return 类型对象
     * @throws BeansException BeansException异常
     */
    public static <T> T getBean(String name) throws BeansException {
        return (T) SpringUtil.beanFactory.getBean(name);
    }

    /**
     * 根据类型获取类型对象
     *
     * @param requiredType 类型
     * @param <T>          返回类型
     * @return 类型对象
     * @throws BeansException BeansException异常
     */
    public static <T> T getBean(Class<T> requiredType) throws BeansException {
        return SpringUtil.beanFactory.getBean(requiredType);
    }

    /**
     * 根据注解和父类的类型获取对象
     *
     * @param annoClass  注解
     * @param superClass 父类的类型
     * @param <T>        返回类型
     * @return 类型对象
     */
    public static <T> List<T> getBeanListBySuperClass(Class<? extends Annotation> annoClass, Class<T> superClass) {
        final Map<String, Object> beansWithAnnotation = SpringUtil.beanFactory.getBeansWithAnnotation(annoClass);
        final List<T> list = beansWithAnnotation.values()
                .stream()
                .filter(item -> item.getClass().getSuperclass().equals(superClass))
                .map(item -> (T) item)
                .collect(Collectors.toList());
        return list;
    }

    /**
     * 根据注解获取对象
     *
     * @param annoClass 注解
     * @return 类型对象集合
     */
    public static Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annoClass) {
        return beanFactory.getBeansWithAnnotation(annoClass);
    }

    /**
     * 获取拥有{annoClass}注解的数量
     *
     * @param annoClass 注解
     * @return 数量
     */
    public static int getBeansWithAnnotationCount(Class<? extends Annotation> annoClass) {
        return beanFactory.getBeansWithAnnotation(annoClass).size();
    }

    /**
     * 判断当前是否有拥有{annoClass}注解的对象
     *
     * @param annoClass 注解
     * @return true：you 反之无
     */
    public static boolean isBeansWithAnnotation(Class<? extends Annotation> annoClass) {
        final Map<String, Object> beansWithAnnotation = SpringUtil.getBeansWithAnnotation(annoClass);
        return beansWithAnnotation.size() > 0;
    }

    /**
     * 根据{annoClass}注解和指定包下获取对象
     *
     * @param annoClass    注解类型
     * @param packagePaths 包集合
     * @return 类型对象
     */
    public static Map<String, Object> getBeansWithAnnotationByPackage(Class<? extends Annotation> annoClass, List<String> packagePaths) {
        final Map<String, Object> beansWithAnnotation = getBeansWithAnnotation(annoClass);
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
    public static boolean isPackage(Object controller, List<String> packagePaths) {
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

    /**
     * 获取接口地址和对应的方法字典集合
     *
     * @param packagePaths 处理包的位置
     * @return 接口地址和对应的方法字典集合
     */
    public static Map<String, ControllerMethod> getControllerMethodMap(List<String> packagePaths) {
        // 接口对应方法
        final Map<String, ControllerMethod> interfacesMethodMap = new HashMap<>(16);
        // 获取所有控制器
        final Map<String, Object> restControllerBeanMap = SpringUtil.getBeansWithAnnotationByPackage(RestController.class, packagePaths);
        // 获取控制器中的方法
        for (Object restControllerBean : restControllerBeanMap.values()) {
            final Class<?> restControllerBeanClass = restControllerBean.getClass();
            final List<String> controllerPathList = getMappingPaths(restControllerBeanClass);
            ReflectionUtils.doWithMethods(restControllerBeanClass, method -> {
                final List<String> methodPathList = getMappingPaths(method);
                ControllerMethod controllerMethod = new ControllerMethod(restControllerBeanClass, method);
                if (methodPathList == null) {
                    return;
                }
                // 拼接接口
                if (controllerPathList == null) {
                    for (String methodPath : methodPathList) {
                        interfacesMethodMap.put(methodPath, controllerMethod);
                    }
                    return;
                }
                for (String controllerPath : controllerPathList) {
                    for (String methodPath : methodPathList) {
                        interfacesMethodMap.put(controllerPath + methodPath, controllerMethod);
                    }
                }
            }, ReflectionUtils.USER_DECLARED_METHODS);
        }
        return interfacesMethodMap;
    }

    /**
     * 获取Mapping注解中的接口地址
     *
     * @param annotatedElement Mapping注解
     * @return 接口地址列表
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
