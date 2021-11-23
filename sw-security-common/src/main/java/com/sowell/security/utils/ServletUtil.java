package com.sowell.security.utils;

import com.sowell.security.context.model.BaseResponse;
import com.sowell.security.exception.base.SecurityException;
import com.sowell.tool.core.bytes.ByteUtil;
import com.sowell.tool.core.enums.RCode;
import com.sowell.tool.json.JsonUtil;

import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Version 版权 Copyright(c)2021 浙江设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/7/1 11:52
 */
public final class ServletUtil {

    /**
     * 发送信息至客户端
     */
    public static <ResponseType> void printResponse(
            BaseResponse<ResponseType> response,
            String contentType,
            RCode rCode
    ) {
        Map<String, Object> resultJson = new LinkedHashMap<>();
        resultJson.put("code", rCode.getCode());
        resultJson.put("data", null);
        resultJson.put("message", rCode.getMessage());
        final String json = JsonUtil.toJsonString(resultJson);
        final byte[] jsonBytes = json.getBytes(StandardCharsets.UTF_8);
        printResponse(response, contentType, jsonBytes);
    }

    /**
     * 发送信息至客户端
     */
    public static <ResponseType> void printResponse(
            BaseResponse<ResponseType> response,
            String contentType,
            SecurityException securityException
    ) {
        final String json = securityException.toJson();
        final byte[] jsonBytes = json.getBytes(StandardCharsets.UTF_8);
        printResponse(response, contentType, jsonBytes);
    }

    /**
     * 发送信息至客户端
     */
    public static <ResponseType> void printResponse(
            BaseResponse<ResponseType> response,
            String contentType,
            String message
    ) {
        final byte[] bytes = ByteUtil.toBytes(message);
        printResponse(response, contentType, bytes);
    }

    /**
     * 发送信息至客户端
     */
    public static <ResponseType> void printResponse(
            BaseResponse<ResponseType> response,
            String contentType,
            byte[] jsonBytes
    ) {
        if (jsonBytes == null || jsonBytes.length < 1) {
            return;
        }
        response.setHeader("Content-Type", contentType);
        final int length = jsonBytes.length;
        response.print(jsonBytes, 0, length);
    }
}
