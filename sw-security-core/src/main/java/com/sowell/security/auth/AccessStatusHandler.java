package com.sowell.security.auth;

import com.sowell.security.model.AuthDetails;

/**
 * @Version 版权 Copyright(c)2021 LZJ
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/6/24 14:53
 */
public interface AccessStatusHandler {

    void verification(AuthDetails<?> authDetails) throws SecurityException;
}
