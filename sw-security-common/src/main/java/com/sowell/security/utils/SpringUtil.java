package com.sowell.security.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Version 版权 Copyright(c)2021 浙江设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/6/20 0:57
 */
@Component
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

    public static <T> List<T> getBeanListBySuperClass(Class<T> superClass) {
        final Map<String, Object> beansWithAnnotation = SpringUtil.beanFactory.getBeansWithAnnotation(Component.class);
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
            String... packagePaths
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
            String... packagePaths
    ) {
        if (packagePaths == null || packagePaths.length < 1) {
            return false;
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
}
