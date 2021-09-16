package com.sowell.security.log;

import javax.servlet.http.HttpServletRequest;

/**
 * @Version 版权 Copyright(c)2021 浙江设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/7/12 10:39
 */
public interface BaseFilterLogHandler {

    /**
     * 过滤前处理
     */
    Object beforeHandler(
            HttpServletRequest request
    );

    /**
     * 过滤后处理
     */
    void afterHandler(
            Object logEntity,
            long requestTime,
            HttpServletRequest request,
            int responseStatus,
            byte[] responseBytes,
            Exception ex
    );
}
