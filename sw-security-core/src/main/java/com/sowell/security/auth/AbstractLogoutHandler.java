package com.sowell.security.auth;

import com.sowell.security.context.model.BaseRequest;
import com.sowell.security.context.model.BaseResponse;

/**
 * @Version 版权 Copyright(c)2021 浙江设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/6/24 15:44
 */
public abstract class AbstractLogoutHandler {

   public abstract boolean logout(
           BaseRequest<?> request,
           BaseResponse<?> response
   );
}
