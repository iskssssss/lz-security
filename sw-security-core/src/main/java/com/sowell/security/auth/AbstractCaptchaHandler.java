package com.sowell.security.auth;

/**
 * @Version 版权 Copyright(c)2021 浙江设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/6/23 17:20
 */
public abstract class AbstractCaptchaHandler {

    /**
     * 验证码处理
     *
     * @param key   键
     * @param value 值
     * @throws SecurityException 异常
     */
    public abstract void handler(
            String key,
            Object value
    ) throws SecurityException;
}
