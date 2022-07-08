package cn.lz.security.config.encrypt;

import cn.lz.security.arrays.UrlHashSet;
import cn.lz.security.defaults.encoder.AESDataEncoder;
import cn.lz.security.handler.DataEncoder;
import cn.lz.tool.core.ArraysUtil;
import cn.lz.tool.core.string.StringUtil;

import java.util.Map;

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
     * 加解密工具
     */
    private Class<? extends DataEncoder> encryptHandlerClass = AESDataEncoder.class;

    /**
     * 参数
     */
    private Map<String, String> params;

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

    public Class<? extends DataEncoder> getEncryptHandlerClass() {
        return encryptHandlerClass;
    }

    public void setEncryptHandlerClass(Class<? extends DataEncoder> encryptHandlerClass) {
        this.encryptHandlerClass = encryptHandlerClass;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
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
        sb.append("\n          ").append("• 密文存放键名").append("：").append(getCipherSaveKey());
        sb.append("\n          ").append("• 加密接口列表").append("：").append(this.getEncryptUrlList().toString());
        return sb.toString();
    }

    @Override
    public String toString() {
        return "EncryptConfig{" +
                "encrypt=" + encrypt +
                ", cipherSaveKey='" + cipherSaveKey + '\'' +
                ", encryptUrlList=" + encryptUrlList +
                '}';
    }
}
