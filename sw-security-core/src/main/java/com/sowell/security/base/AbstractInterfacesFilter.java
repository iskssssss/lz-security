package com.sowell.security.base;

import com.sowell.security.context.model.BaseRequest;
import com.sowell.security.context.model.BaseResponse;
import com.sowell.security.exception.SecurityException;

/**
 * 接口过滤执行链
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @date 2021/7/7 11:26
 */
public abstract class AbstractInterfacesFilter extends InterfacesFilterBuilder {

    /**
     * 过滤器初始化
     */
    public abstract void init();

    /**
     * 进行过滤
     *
     * @param request  请求流
     * @param response 响应流
     * @param params   过滤参数
     * @return 过滤结果
     * @throws SecurityException 过滤错误
     */
    public abstract boolean doFilter(BaseRequest<?> request, BaseResponse<?> response, Object... params) throws SecurityException;

    /**
     * 销毁
     */
    public abstract void destroy();
}
