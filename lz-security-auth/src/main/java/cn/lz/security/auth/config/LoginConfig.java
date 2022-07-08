package cn.lz.security.auth.config;

/**
 * TODO
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2022 杭州设维信息技术有限公司
 * @date 2022/7/1 16:57
 */
public class LoginConfig {

    /**
     * 登录地址
     */
    private String loginUrl = "/api/login/login.do";
    /**
     * 存放标识的键值
     */
    private String identifierKey = "username";
    /**
     * 存放凭据的键值
     */
    private String credentialKey = "password";
    /**
     * 存放验证码的键值
     */
    private String codeKey = "code";
    /**
     * 存放键值的键值
     */
    private String keyKey = "uuid";
    /**
     * 存放记住我的键值
     */
    private String rememberMeKey = "rememberMe";

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public String getIdentifierKey() {
        return identifierKey;
    }

    public void setIdentifierKey(String identifierKey) {
        this.identifierKey = identifierKey;
    }

    public String getCredentialKey() {
        return credentialKey;
    }

    public void setCredentialKey(String credentialKey) {
        this.credentialKey = credentialKey;
    }

    public String getKeyKey() {
        return keyKey;
    }

    public void setKeyKey(String keyKey) {
        this.keyKey = keyKey;
    }

    public String getCodeKey() {
        return codeKey;
    }

    public void setCodeKey(String codeKey) {
        this.codeKey = codeKey;
    }

    public String getRememberMeKey() {
        return rememberMeKey;
    }

    public void setRememberMeKey(String rememberMeKey) {
        this.rememberMeKey = rememberMeKey;
    }

    @Override
    public String toString() {
        return "LoginConfig{" +
                "loginUrl='" + loginUrl + '\'' +
                ", identifierKey='" + identifierKey + '\'' +
                ", credentialKey='" + credentialKey + '\'' +
                ", codeKey='" + codeKey + '\'' +
                ", rememberMeKey='" + rememberMeKey + '\'' +
                '}';
    }
}
