package cn.lz.security.auth.config;

/**
 * TODO
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2022 LZJ
 * @date 2022/7/1 16:57
 */
public class LogoutConfig {
    /**
     * 登出地址
     */
    protected String logoutUrl = "/api/logout/logout.do";

    public String getLogoutUrl() {
        return logoutUrl;
    }

    public void setLogoutUrl(String logoutUrl) {
        this.logoutUrl = logoutUrl;
    }

    @Override
    public String toString() {
        return "LogoutConfig{" +
                "logoutUrl='" + logoutUrl + '\'' +
                '}';
    }
}
