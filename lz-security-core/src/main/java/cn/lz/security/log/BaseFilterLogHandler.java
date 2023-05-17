package cn.lz.security.log;

import cn.lz.security.context.model.BaseRequest;
import cn.lz.security.context.model.BaseResponse;

/**
 * 过滤日志处理器
 * <p>可重写相应方法来记录日志信息</p>
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 LZ
 * @date 2021/7/12 10:39
 */
public interface BaseFilterLogHandler {

    /**
     * 过滤前日志记录
     *
     * @param request  请求信息
     * @param response 响应信息
     * @return 返回对象 用于{@link BaseFilterLogHandler#after(BaseRequest, BaseResponse, Object, Exception)}方法使用
     */
    default Object before(BaseRequest<?> request, BaseResponse<?> response) {
        return null;
    }

    /**
     * 过滤后日志记录
     *
     * @param request   请求信息
     * @param response  响应信息
     * @param ex        错误信息
     * @param logEntity 由{@link BaseFilterLogHandler#before(BaseRequest, BaseResponse)}方法提供
     */
    default void after(BaseRequest<?> request, BaseResponse<?> response, Object logEntity, Exception ex) {
    }
}
