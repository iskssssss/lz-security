package com.sowell.security.auth.handler;

import com.sowell.tool.jwt.model.AuthDetails;

/**
 * @Version 版权 Copyright(c)2021 浙江设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/6/24 14:53
 */
public interface AccessStatusHandler {

    void verification(AuthDetails<?> authDetails) throws SecurityException;
}
