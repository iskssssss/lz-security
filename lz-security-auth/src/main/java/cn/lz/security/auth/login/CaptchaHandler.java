package cn.lz.security.auth.login;

import cn.hutool.core.util.IdUtil;
import cn.lz.security.auth.model.CaptchaModel;

/**
 * @Version 版权 Copyright(c)2021 LZ
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/6/23 17:20
 */
public interface CaptchaHandler {

    /**
     * 验证码验证
     *
     * @param key   键
     * @param value 值
     * @throws SecurityException 异常
     */
    void check(Object key, Object value) throws SecurityException;

    /**
     * 生成验证码
     *
     * @return 结果
     */
    default CaptchaModel create() {
        return this.create(IdUtil.fastSimpleUUID(), IdUtil.fastSimpleUUID().substring(0, 6));
    }

    /**
     * 生成验证码
     *
     * @param key 键值
     * @return 结果
     */
    default CaptchaModel createKey(String key) {
        return this.create(key, IdUtil.fastSimpleUUID().substring(0, 6));
    }

    /**
     * 生成验证码
     *
     * @param code 验证码
     * @return 结果
     */
    default CaptchaModel createCode(String code) {
        return this.create(IdUtil.fastSimpleUUID(), code);
    }

    /**
     * 生成验证码
     *
     * @param key  键值
     * @param code 验证码
     * @return 结果
     */
    CaptchaModel create(String key, String code);
}
