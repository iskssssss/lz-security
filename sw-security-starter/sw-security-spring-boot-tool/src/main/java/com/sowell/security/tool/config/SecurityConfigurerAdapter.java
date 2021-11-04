package com.sowell.security.tool.config;

import com.sowell.security.IcpCoreManager;
import com.sowell.security.config.CoreConfigurer;
import com.sowell.security.config.CoreConfigurerBuilder;
import com.sowell.security.filter.config.IcpConfig;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

/**
 * @Version 版权 Copyright(c)2021 浙江设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/6/21 9:08
 */
public abstract class SecurityConfigurerAdapter implements EnvironmentAware {

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
        IcpConfig icpConfig = Binder.get(environment).bind("sw.security", IcpConfig.class).get();
        IcpCoreManager.setIcpConfig(icpConfig);
        this.config(IcpCoreManager.getCoreConfigurer());
        this.init();
    }
}
