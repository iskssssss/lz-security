package com.sowell.security.filter.utils;

import com.alibaba.fastjson.JSONObject;
import com.sowell.security.filter.context.model.BaseResponse;
import com.sowell.tool.core.enums.RCode;
import com.sowell.security.exception.SecurityException;
import com.sowell.tool.core.bytes.ByteUtil;
import com.sowell.tool.json.JsonUtil;

import java.nio.charset.StandardCharsets;

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
        JSONObject resultJson = new JSONObject();
        resultJson.put("code", rCode.getCode());
        resultJson.put("data", null);
        resultJson.put("message", rCode.getMessage());
        final String json = resultJson.toJSONString();
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
        JSONObject resultJson = new JSONObject();
        final Object responseData = securityException.getResponseData();
        resultJson.put("code", securityException.getCode());
        resultJson.put("data", responseData == null ? null : JsonUtil.toJsonString(responseData));
        resultJson.put("message", securityException.getMessage());
        final String json = resultJson.toJSONString();
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
