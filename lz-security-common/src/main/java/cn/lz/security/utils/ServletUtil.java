package cn.lz.security.utils;

import cn.lz.security.context.model.BaseResponse;
import cn.lz.security.exception.base.SecurityException;
import cn.lz.tool.core.bytes.ByteUtil;
import cn.lz.tool.core.enums.RCode;
import cn.lz.tool.core.string.StringUtil;
import cn.lz.tool.http.enums.MediaType;
import cn.lz.tool.io.FileUtil;
import cn.lz.tool.json.JsonUtil;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Version 版权 Copyright(c)2021 LZ
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
        if (StringUtil.isNotEmpty(contentType)) {
            response.setHeader("Content-Type", contentType);
        }
        final int length = jsonBytes.length;
        response.print(jsonBytes, 0, length);
    }

    /**
     * 将文件写入客户端
     */
    public static <ResponseType> void download(
            BaseResponse<ResponseType> response,
            String contentType,
            String fileName,
            byte[] fileBytes
    ) throws UnsupportedEncodingException {
        if (fileBytes == null || fileBytes.length < 1) {
            return;
        }
        if (StringUtil.isEmpty(contentType)) {
            contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }
        response.setHeader("Content-Type", contentType);
        response.setHeader("Content-Disposition", "attachment;filename=\"" + URLEncoder.encode(fileName, StandardCharsets.UTF_8.name()) + "\"");
        final int length = fileBytes.length;
        response.print(fileBytes, 0, length);
    }

    /**
     * 将文件写入客户端
     */
    public static <ResponseType> void download(
            BaseResponse<ResponseType> response,
            String fileName,
            byte[] fileBytes
    ) throws UnsupportedEncodingException {
        ServletUtil.download(response, MediaType.APPLICATION_OCTET_STREAM_VALUE, fileName, fileBytes);
    }

    /**
     * 将文件写入客户端
     */
    public static <ResponseType> void download(
            BaseResponse<ResponseType> response,
            File file
    ) throws UnsupportedEncodingException {
        ServletUtil.download(response, file.getName(), file);
    }

    /**
     * 将文件写入客户端
     */
    public static <ResponseType> void download(
            BaseResponse<ResponseType> response,
            String fileName,
            File file
    ) throws UnsupportedEncodingException {
        ServletUtil.download(response, MediaType.APPLICATION_OCTET_STREAM_VALUE, fileName, file);
    }

    /**
     * 将文件写入客户端
     */
    public static <ResponseType> void download(
            BaseResponse<ResponseType> response,
            String contentType,
            String fileName,
            File file
    ) throws UnsupportedEncodingException {
        byte[] fileBytes = FileUtil.readBytes(file);
        ServletUtil.download(response, contentType, fileName, fileBytes);
    }
}
