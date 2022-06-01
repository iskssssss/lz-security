package cn.lz.security.filter.filters;

import cn.lz.security.context.model.BaseRequest;
import cn.lz.security.context.model.BaseResponse;
import cn.lz.security.exception.base.SecurityException;
import cn.lz.security.log.LzLogger;
import cn.lz.security.log.LzLoggerUtil;

/**
 * @Version 版权 Copyright(c)2021 LZ
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/6/25 12:23
 */
public class StartFilter extends AbsInterfacesFilterBuilder {
    protected final LzLogger lzLogger = LzLoggerUtil.getLzLogger(StartFilter.class);

    @Override
    public void init() { }

    @Override
    public boolean doFilter(BaseRequest<?> request, BaseResponse<?> response, Object... params) throws SecurityException {
        lzLogger.info("过滤接口：" + request.getRequestPath());
        return super.next(request, response, params);
    }

    @Override
    public void destroy() { }
}
