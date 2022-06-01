package cn.lz.tool.encrypt.model;

import cn.lz.tool.core.model.BaseModel;
import cn.lz.tool.encrypt.SecretUtils;

/**
 * @version 版权 Copyright(c)2021 LZ
 * @ClassName:
 * @Descripton:
 * @author: 孔胜
 * @date: 2021/5/7 15:33
 */
public class AppKeyPair extends BaseModel {
    private String seed;
    private String appKey;
    private String appSecret;

    public AppKeyPair(){

    }

    public AppKeyPair(String seed, String appKey, String appSecret) {
        this.seed = seed;
        this.appKey = appKey;
        this.appSecret = appSecret;
    }

    public String getSeed() {
        return seed;
    }

    public void setSeed(String seed) {
        this.seed = seed;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public static AppKeyPair generateAppPair() {
        final String seed = SecretUtils.randomSeed();
        return new AppKeyPair(seed, SecretUtils.appKey(seed), SecretUtils.appSecret(seed));
    }

    public static AppKeyPair generateAppPair(String seed) {
        return new AppKeyPair(seed, SecretUtils.appKey(seed), SecretUtils.appSecret(seed));
    }
}
