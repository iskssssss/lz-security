package cn.lz.security.auth.handler;

/**
 * @Version 版权 Copyright(c)2021 LZ
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/6/23 17:20
 */
public interface CaptchaHandler {

    /**
     * 验证码处理
     *
     * @param value 值
     * @throws SecurityException 异常
     */
    void handler(Object value) throws SecurityException;
}
