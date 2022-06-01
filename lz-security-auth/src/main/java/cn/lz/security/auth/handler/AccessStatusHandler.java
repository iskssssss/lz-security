package cn.lz.security.auth.handler;

import cn.lz.tool.jwt.model.AuthDetails;

/**
 * @Version 版权 Copyright(c)2021 LZ
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/6/24 14:53
 */
public interface AccessStatusHandler {

    void verification(AuthDetails<?> authDetails) throws SecurityException;
}
