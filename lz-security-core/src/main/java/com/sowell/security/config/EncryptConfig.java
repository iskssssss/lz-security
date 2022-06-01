package com.sowell.security.config;

import com.sowell.security.arrays.UrlHashSet;
import com.sowell.tool.core.ArraysUtil;
import com.sowell.tool.core.string.StringUtil;
import com.sowell.tool.encrypt.model.SwPrivateKey;
import com.sowell.tool.encrypt.model.SwPublicKey;

/**
 * 加解密配置
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2022 LZ
 * @date 2022/6/1 10:17
 */
public class EncryptConfig {
    /**
     * 是否加密
     */
    private Boolean encrypt = false;
    /**
     * 公钥
     */
    private SwPublicKey publicKeyStr;
    /**
     * 私钥
     */
    private SwPrivateKey privateKeyStr;

    /**
     * 加密接口列表（逗号(,)隔开）
     * <p>/a/**,/b 或 /a/**, /b</p>
     */
    private UrlHashSet encryptUrlList;

    /**
     * 获取是否加密
     *
     * @return 是否加密
     */
    public Boolean getEncrypt() {
        return encrypt;
    }

    /**
     * 设置是否加密
     *
     * @param encrypt 是否加密
     */
    public void setEncrypt(Boolean encrypt) {
        this.encrypt = encrypt;
    }

    /**
     * 获取公钥
     *
     * @return 公钥
     */
    public SwPublicKey getPublicKeyStr() {
        return publicKeyStr;
    }

    /**
     * 设置公钥
     *
     * @param publicKeyStr 公钥
     */
    public void setPublicKeyStr(String publicKeyStr) {
        if (StringUtil.isEmpty(publicKeyStr)) {
            return;
        }
        this.publicKeyStr = new SwPublicKey(publicKeyStr);
    }

    /**
     * 获取私钥
     *
     * @return 私钥
     */
    public SwPrivateKey getPrivateKeyStr() {
        return privateKeyStr;
    }

    /**
     * 设置私钥
     *
     * @param privateKeyStr 私钥
     */
    public void setPrivateKeyStr(String privateKeyStr) {
        if (StringUtil.isEmpty(privateKeyStr)) {
            return;
        }
        this.privateKeyStr = new SwPrivateKey(privateKeyStr);
    }

    /**
     * 获取加密接口列表
     *
     * @return 加密接口列表
     */
    public UrlHashSet getEncryptUrlList() {
        if (this.encryptUrlList == null) {
            this.encryptUrlList = UrlHashSet.empty();
        }
        return this.encryptUrlList;
    }

    /**
     * 设置加密接口列表
     *
     * @param encryptUrlList 加密接口列表
     */
    public void setEncryptUrlList(String encryptUrlList) {
        if (StringUtil.isEmpty(encryptUrlList)) {
            this.encryptUrlList = UrlHashSet.empty();
            return;
        }
        this.encryptUrlList = new UrlHashSet(ArraysUtil.toList(StringUtil.delAllSpace(encryptUrlList), ",", true));
    }

    public String print() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n          ").append("• 是否加密").append("：").append(encrypt);
        sb.append("\n          ").append("• 私钥").append("：").append(privateKeyStr.getPrivateKeyStr());
        sb.append("\n          ").append("• 公钥").append("：").append(publicKeyStr.getPublicKeyStr());
        sb.append("\n          ").append("• 加密接口列表").append("：").append(this.getEncryptUrlList().toString());
        return sb.toString();
    }

    @Override
    public String toString() {
        return "EncryptConfig{" +
                "encrypt=" + encrypt +
                ", publicKeyStr=" + publicKeyStr +
                ", privateKeyStr=" + privateKeyStr +
                ", encryptUrlList=" + encryptUrlList +
                '}';
    }
}
