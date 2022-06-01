package cn.lz.security.tool.config;

import cn.lz.security.LzCoreManager;
import cn.lz.security.config.CoreConfigurer;
import cn.lz.security.config.LzConfig;
import cn.lz.security.config.CoreConfigurerBuilder;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

/**
 * @Version 版权 Copyright(c)2021 LZ
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/6/21 9:08
 */
public abstract class BaseSecurityConfigurerAdapter implements EnvironmentAware {

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
    public void setEnvironment(Environment environment) {
        LzConfig lzConfig = Binder.get(environment).bind("lz.security", LzConfig.class).get();
        LzCoreManager.setLzConfig(lzConfig);
        this.config(LzCoreManager.getCoreConfigurer());
        this.init();
    }
}
