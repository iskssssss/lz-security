package cn.lz.security.auth.handler;

import cn.lz.security.context.model.BaseRequest;
import cn.lz.security.context.model.BaseResponse;

/**
 * @Version 版权 Copyright(c)2021 LZ
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/6/24 8:56
 */
public abstract class AbstractAuthorizationHandler {

    public abstract boolean authorization(
            BaseRequest<?> request,
            BaseResponse<?> response
    );
}
