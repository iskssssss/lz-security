package cn.lz.security.auth.logout;

import cn.lz.security.context.model.BaseRequest;
import cn.lz.security.context.model.BaseResponse;

/**
 * @Version 版权 Copyright(c)2021 LZ
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
