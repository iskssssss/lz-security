package com.sowell.security.utils;

import com.alibaba.fastjson.JSONObject;
import com.sowell.security.context.model.BaseResponse;
import com.sowell.security.enums.RCode;

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
            RCode rCode
    ) {
        JSONObject resultJson = new JSONObject();
        resultJson.put("code", rCode.getCode());
        resultJson.put("data", null);
        resultJson.put("message", rCode.getMessage());
        final String json = resultJson.toJSONString();
        final byte[] jsonBytes = json.getBytes(StandardCharsets.UTF_8);
        printResponse(response, jsonBytes);
    }

    /**
     * 发送信息至客户端
     */
    public static <ResponseType> void printResponse(
            BaseResponse<ResponseType> response,
            String message
    ) {
        final byte[] bytes = ByteUtil.toBytes(message);
        printResponse(response, bytes);
    }

    /**
     * 发送信息至客户端
     */
    public static <ResponseType> void printResponse(
            BaseResponse<ResponseType> response,
            byte[] jsonBytes
    ) {
        response.setHeader("Content-Type", "text/json;charset=utf-8");
        final int length = jsonBytes.length;
        response.print(jsonBytes, 0, length);
    }
}
