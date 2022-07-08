package cn.lz.security.handler;

import cn.lz.security.context.model.BaseRequest;
import cn.lz.security.context.model.BaseResponse;
import cn.lz.security.exception.base.SecurityException;

/**
 * 跨域处理器
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2022 杭州设维信息技术有限公司
 * @date 2022/7/5 16:32
 */
public interface CorsHandler {

    /**
     * 处理跨域
     *
     * @param request          请求流
     * @param response         响应流
     * @param preFlightRequest 是否是OPTIONS请求
     * @throws SecurityException 跨域异常
     */
    void handler(BaseRequest<?> request, BaseResponse<?> response, boolean preFlightRequest) throws SecurityException;
}
