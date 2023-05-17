package cn.lz.security.auth.login;

import cn.lz.security.context.model.BaseRequest;
import cn.lz.security.context.model.BaseResponse;

/**
 * @Version 版权 Copyright(c)2021 LZ
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/6/24 8:56
 */
public interface LoginService {

    boolean login(BaseRequest<?> request, BaseResponse<?> response);
}
