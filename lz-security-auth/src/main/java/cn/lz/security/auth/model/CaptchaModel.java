package cn.lz.security.auth.model;

import cn.lz.tool.core.model.BaseModel;

import java.io.Serializable;

/**
 * 验证码 对象
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2022 杭州设维信息技术有限公司
 * @date 2022/7/6 11:21
 */
public class CaptchaModel extends BaseModel {

    /**
     * 键值
     * <p>用户校验验证码</p>
     */
    private String key;

    /**
     * 通用字段
     * <p>由使用方自行定义</p>
     */
    private Object value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "CaptchaModel{" +
                "key='" + key + '\'' +
                ", value=" + value +
                '}';
    }
}
