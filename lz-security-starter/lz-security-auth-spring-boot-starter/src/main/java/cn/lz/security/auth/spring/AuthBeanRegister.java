package cn.lz.security.auth.spring;

import cn.lz.security.auth.config.AuthConfig;
import cn.lz.security.auth.config.LoginConfig;
import cn.lz.security.auth.config.LogoutConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * TODO
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2022 LZJ
 * @date 2022/7/1 17:04
 */
public class AuthBeanRegister {

    @Bean
    @ConfigurationProperties("lz.security.auth-config")
    public AuthConfig registerAuthConfig() {
        return new AuthConfig();
    }

    @Bean
    @ConfigurationProperties("lz.security.auth-config.login-config")
    public LoginConfig registerLoginConfig() {
        return new LoginConfig();
    }

    @Bean
    @ConfigurationProperties("lz.security.auth-config.logout-config")
    public LogoutConfig registerLogoutConfig() {
        return new LogoutConfig();
    }
}
