package cn.lz.security.config;

import cn.lz.security.arrays.UrlHashSet;
import cn.lz.tool.core.ArraysUtil;
import cn.lz.tool.core.string.StringUtil;
import cn.lz.tool.encrypt.model.SwPrivateKey;
import cn.lz.tool.encrypt.model.SwPublicKey;

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
    private SwPublicKey publicKey;
    /**
     * 私钥
     */
    private SwPrivateKey privateKey;
    /**
     * 公钥
     */
    private String pubKey;
    /**
     * 私钥
     */
    private String priKey;


    /**
     * 请求加密后 密文存放键名
     */
    private String cipherSaveKey;

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
    public SwPublicKey getPublicKey() {
        return publicKey;
    }

    /**
     * 获取公钥
     *
     * @return 公钥
     */
    public String getPubKey() {
        return pubKey;
    }

    /**
     * 设置公钥
     *
     * @param pubKey 公钥
     */
    public void setPubKey(String pubKey) {
        if (StringUtil.isEmpty(pubKey)) {
            return;
        }
        this.pubKey = pubKey;
        this.publicKey = new SwPublicKey(pubKey);
    }

    /**
     * 获取私钥
     *
     * @return 私钥
     */
    public SwPrivateKey getPrivateKey() {
        return privateKey;
    }

    /**
     * 获取私钥
     *
     * @return 私钥
     */
    public String getPriKey() {
        return priKey;
    }

    /**
     * 设置私钥
     *
     * @param priKey 私钥
     */
    public void setPriKey(String priKey) {
        if (StringUtil.isEmpty(priKey)) {
            return;
        }
        this.priKey = priKey;
        this.privateKey = new SwPrivateKey(priKey);
    }

    /**
     * 获取密文存放键名
     *
     * @return 密文存放键名
     */
    public String getCipherSaveKey() {
        return this.cipherSaveKey;
    }

    /**
     * 设置密文存放键名
     *
     * @param cipherSaveKey 密文存放键名
     */
    public void setCipherSaveKey(String cipherSaveKey) {
        this.cipherSaveKey = cipherSaveKey;
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
        sb.append("\n          ").append("• 是否加密").append("：").append(getEncrypt());
        sb.append("\n          ").append("• 私钥").append("：").append(priKey);
        sb.append("\n          ").append("• 公钥").append("：").append(pubKey);
        sb.append("\n          ").append("• 密文存放键名").append("：").append(getCipherSaveKey());
        sb.append("\n          ").append("• 加密接口列表").append("：").append(this.getEncryptUrlList().toString());
        return sb.toString();
    }

    @Override
    public String toString() {
        return "EncryptConfig{" +
                "encrypt=" + encrypt +
                ", publicKey=" + publicKey +
                ", privateKey=" + privateKey +
                ", pubKey='" + pubKey + '\'' +
                ", priKey='" + priKey + '\'' +
                ", cipherSaveKey='" + cipherSaveKey + '\'' +
                ", encryptUrlList=" + encryptUrlList +
                '}';
    }
}
