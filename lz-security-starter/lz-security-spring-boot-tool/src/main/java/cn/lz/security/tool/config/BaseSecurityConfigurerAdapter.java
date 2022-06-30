package cn.lz.security.tool.config;

import cn.lz.security.LzCoreManager;
import cn.lz.security.config.*;
import lombok.SneakyThrows;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.util.CollectionUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * @Version 版权 Copyright(c)2021 LZ
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/6/21 9:08
 */
public abstract class BaseSecurityConfigurerAdapter implements EnvironmentAware, WebMvcConfigurer {

    /**
     * 初始化
     */
    protected abstract void init();

    /**
     * 配置基础信息
     *
     * @param coreConfigurer 配置信息
     */
    protected abstract void config(CoreConfigurerBuilder<CoreConfigurer> coreConfigurer);

    @Override
    public final void addCorsMappings(CorsRegistry registry) {
        CorsConfig corsConfig = LzCoreManager.getCorsConfig();
        List<CorsConfig.CorsRegistration> corsRegistrationList = corsConfig.getCorsRegistrationList();
        LzCorsRegistry lzCorsRegistry = new LzCorsRegistry();
        this.initCorsInfo(lzCorsRegistry);
        Map<String, CorsConfiguration> corsConfigurations = lzCorsRegistry.getCorsConfigurations();
        for (String key : corsConfigurations.keySet()) {
            CorsConfiguration corsConfiguration = corsConfigurations.get(key);
            CorsConfig.CorsRegistration corsRegistration = new CorsConfig.CorsRegistration();
            corsRegistration.setPathPattern(key);
            CorsConfig.CorsItemConfig corsItemConfig = new CorsConfig.CorsItemConfig();
            corsItemConfig.setAllowedHeaders(corsConfiguration.getAllowedHeaders());
            corsItemConfig.setAllowCredentials(corsConfiguration.getAllowCredentials());
            corsItemConfig.setAllowedMethods(corsConfiguration.getAllowedMethods());
            corsItemConfig.setMaxAge(corsConfiguration.getMaxAge());
            corsItemConfig.setAllowedOrigins(corsConfiguration.getAllowedOrigins());
            corsItemConfig.setExposedHeaders(corsConfiguration.getExposedHeaders());
            corsRegistration.setConfig(corsItemConfig);
            corsRegistrationList.add(corsRegistration);
            this.initSpringMvcCorsInfo(registry, key, corsConfiguration);
        }
    }

    /**
     * 初始化跨域信息
     *
     * @param registry 跨域配置
     */
    protected void initCorsInfo(CorsRegistry registry) {
    }

    @Override
    public void setEnvironment(Environment environment) {
        LzConfig lzConfig = Binder.get(environment).bind("lz.security", LzConfig.class).orElse(new LzConfig());
        TokenConfig tokenConfig = Binder.get(environment).bind("lz.security.token-config", TokenConfig.class).orElse(new TokenConfig());
        EncryptConfig encryptConfig = Binder.get(environment).bind("lz.security.encrypt-config", EncryptConfig.class).orElse(new EncryptConfig());
        FilterConfig filterConfig = Binder.get(environment).bind("lz.security.filter-config", FilterConfig.class).orElse(new FilterConfig());
        //CorsConfig corsConfig = Binder.get(environment).bind("lz.security.cors-config", CorsConfig.class).orElse(new CorsConfig());
        LzCoreManager.setLzConfig(lzConfig);
        LzCoreManager.setTokenConfig(tokenConfig);
        LzCoreManager.setEncryptConfig(encryptConfig);
        LzCoreManager.setFilterConfig(filterConfig);
        //LzCoreManager.setCorsConfig(corsConfig);
        this.config(LzCoreManager.getCoreConfigurer());
        this.init();
    }

    /**
     * 初始化SpringMvc跨域配置
     *
     * @param registry          SpringMvc跨域配置
     * @param key               路径
     * @param corsConfiguration 自定跨域配置
     */
    private void initSpringMvcCorsInfo(CorsRegistry registry, String key, CorsConfiguration corsConfiguration) {
        CorsRegistration corsRegistration = registry.addMapping(key);
        Class<CorsRegistration> corsRegistrationClass = CorsRegistration.class;
        try {
            Field config = corsRegistrationClass.getDeclaredField("config");
            config.setAccessible(true);
            CorsConfiguration configuration = ((CorsConfiguration) config.get(corsRegistration));
            configuration.setAllowedHeaders(corsConfiguration.getAllowedHeaders());
            configuration.setAllowCredentials(corsConfiguration.getAllowCredentials());
            configuration.setAllowedMethods(corsConfiguration.getAllowedMethods());
            configuration.setMaxAge(corsConfiguration.getMaxAge());
            configuration.setAllowedOrigins(corsConfiguration.getAllowedOrigins());
            configuration.setExposedHeaders(corsConfiguration.getExposedHeaders());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private static class LzCorsRegistry extends CorsRegistry {

        /**
         * 获取跨域配置信息
         *
         * @return 跨域配置信息
         */
        public Map<String, CorsConfiguration> getCorsConfigurations() {
            return super.getCorsConfigurations();
        }
    }
}
