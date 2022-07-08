package cn.lz.security.defaults;

import cn.lz.security.LzConstant;
import cn.lz.security.LzCoreManager;
import cn.lz.security.config.CorsConfig;
import cn.lz.security.context.model.BaseRequest;
import cn.lz.security.context.model.BaseResponse;
import cn.lz.security.exception.CorsException;
import cn.lz.security.handler.CorsHandler;
import cn.lz.security.log.LzLoggerUtil;
import cn.lz.tool.core.ObjectUtil;
import cn.lz.tool.http.enums.HeaderEnums;
import cn.lz.tool.http.enums.RequestMethodEnum;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static cn.lz.tool.http.enums.HeaderEnums.*;

/**
 * 跨域处理 默认实现
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2022 杭州设维信息技术有限公司
 * @date 2022/7/5 16:34
 */
public class CorsHandlerDefault implements CorsHandler {

    private static final String allMethod = RequestMethodEnum.allJoin();

    @Override
    public void handler(BaseRequest<?> request, BaseResponse<?> response, boolean preFlightRequest) throws SecurityException {
        Object corsHandlerStatus = request.getAttribute(LzConstant.CORS_HANDLER_STATUS_KEY);
        if (ObjectUtil.equals(corsHandlerStatus, true)) {
            return;
        }
        request.setAttribute(LzConstant.CORS_HANDLER_STATUS_KEY, true);
        CorsConfig corsConfig = LzCoreManager.getCorsConfig();
        String requestOrigin = request.getHeader(ORIGIN);
        List<CorsConfig.CorsRegistration> corsRegistrationList = corsConfig.getCorsRegistrationList();
        if (corsRegistrationList == null || corsRegistrationList.isEmpty()) {
            return;
        }
        LzLoggerUtil.info(CorsHandlerDefault.class, "跨域处理");
        for (CorsConfig.CorsRegistration corsRegistration : corsRegistrationList) {
            String pathPattern = corsRegistration.getPathPattern();
            if (!request.matchUrl(pathPattern)) {
                continue;
            }
            CorsConfig.CorsItemConfig config = corsRegistration.getConfig();
            String allowOrigin = config.checkOrigin(requestOrigin);
            List<String> allowedMethods = config.getAllowedMethods();
            List<String> allowedHeaders = config.getAllowedHeaders();
            if (allowOrigin == null) {
                throw new CorsException();
            }
            if (CorsConfig.CorsItemConfig.ALL.equals(allowOrigin)) {
                response.addHeader(VARY, ORIGIN);
            }
            if (allowedMethods.contains(CorsConfig.CorsItemConfig.ALL)) {
                response.addHeader(VARY, ACCESS_CONTROL_REQUEST_METHOD);
            }
            if (allowedHeaders.contains(CorsConfig.CorsItemConfig.ALL)) {
                response.addHeader(VARY, ACCESS_CONTROL_REQUEST_HEADERS);
            }
            if (preFlightRequest) {
                Set<String> accessControlAllowMethodSet = new HashSet<>(allowedMethods);
                if (allowedMethods.contains(CorsConfig.CorsItemConfig.ALL)) {
                    String accessControlRequestMethod = request.getHeader(HeaderEnums.ACCESS_CONTROL_REQUEST_METHOD).replaceAll(" ", "");
                    accessControlAllowMethodSet.addAll(Arrays.stream(accessControlRequestMethod.split(",")).collect(Collectors.toSet()));
                    accessControlAllowMethodSet.remove(CorsConfig.CorsItemConfig.ALL);
                }
                response.setHeader(ACCESS_CONTROL_ALLOW_METHODS, accessControlAllowMethodSet);

                Set<String> accessControlAllowHeaderSet = new HashSet<>(allowedHeaders);
                if (allowedHeaders.contains(CorsConfig.CorsItemConfig.ALL)) {
                    String accessControlRequestHeaders = request.getHeader(HeaderEnums.ACCESS_CONTROL_REQUEST_HEADERS).replaceAll(" ", "");
                    accessControlAllowHeaderSet.addAll(Arrays.stream(accessControlRequestHeaders.split(",")).collect(Collectors.toSet()));
                    accessControlAllowHeaderSet.remove(CorsConfig.CorsItemConfig.ALL);
                }
                response.setHeader(ACCESS_CONTROL_ALLOW_HEADERS, accessControlAllowHeaderSet);
                response.setHeader(ALLOW, allMethod);
            }
            response
                    .setHeader(ACCESS_CONTROL_ALLOW_CREDENTIALS, String.valueOf(config.getAllowCredentials()))
                    .setHeader(ACCESS_CONTROL_ALLOW_ORIGIN, allowOrigin)
                    .setHeader(ACCESS_CONTROL_EXPOSE_HEADERS, config.getExposedHeaders())
                    .setHeader(ACCESS_CONTROL_MAX_AGE, String.valueOf(config.getMaxAge()));
        }
    }
}
