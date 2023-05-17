package cn.lz.security.auth.logout;

import cn.lz.security.context.model.BaseRequest;
import cn.lz.security.context.model.BaseResponse;
import cn.lz.security.token.AccessTokenUtil;
import cn.lz.security.utils.ServletUtil;
import cn.lz.tool.core.enums.RCode;
import cn.lz.tool.core.model.RequestResult;
import cn.lz.tool.http.enums.MediaType;

import java.nio.charset.StandardCharsets;

/**
 * 登出服务类 默认实现类
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2022 LZJ
 * @date 2022/6/30 17:52
 */
public class LogoutServiceDefault implements LogoutService {

    @Override
    public boolean logout(BaseRequest<?> request, BaseResponse<?> response) {
        response.setStatus(200);
        AccessTokenUtil.invalid();
        RequestResult requestResult = new RequestResult();
        requestResult.setCode(RCode.SUCCESS.getCode());
        requestResult.setMessage("注销成功。");
        ServletUtil.printResponse(response, MediaType.APPLICATION_JSON_VALUE, requestResult.toJson().getBytes(StandardCharsets.UTF_8));
        return false;
    }
}
