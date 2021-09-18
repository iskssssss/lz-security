package com.sowell.security.utils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @version 版权 Copyright(c)2021 浙江设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @author: 孔胜
 * @date: 2021/5/12 10:14
 */
public final class FileUtil extends cn.hutool.core.io.FileUtil {

    /**
     * 将文件写入输出流并关闭
     *
     * @param outputStream 输出流
     * @param path         文件路径
     * @return 输出流
     */
    public static String writeFileForResponseClose(OutputStream outputStream, String path) {
        try (
                final BufferedInputStream inputStream = FileUtil.getInputStream(path)
        ) {
            FileUtil.writeFileForResponseClose(outputStream, inputStream);
            return null;
        } catch (IOException ioException) {
            return "，文件写入失败";
        }
    }

    /**
     * 将文件写入输出流并关闭
     *
     * @param outputStream 输出流
     * @param inputStream  输入流
     * @return 是否写入成功
     */
    public static String writeFileForResponseClose(OutputStream outputStream, InputStream inputStream) {
        try {
            byte[] bytes = new byte[1024];
            int len;
            while ((len = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
            }
            return null;
        } catch (IOException ioException) {
            return "，文件写入失败";
        } finally {
            try {
                outputStream.close();
            } catch (IOException ioException) {
            }
        }
    }

    /**
     * 将文件写入输出流
     *
     * @param outputStream 输出流
     * @param path         文件路径
     * @return 输出流
     */
    public static OutputStream writeFileForResponse(OutputStream outputStream, String path) {
        try (
                final BufferedInputStream inputStream = FileUtil.getInputStream(path)
        ) {
            return FileUtil.writeFileForResponse(outputStream, inputStream);
        } catch (IOException ioException) {
            throw new RuntimeException("，文件写入失败", ioException);
        }
    }

    /**
     * 将文件写入输出流
     *
     * @param outputStream 输出流
     * @param inputStream  输入流
     * @return 输出流
     */
    public static OutputStream writeFileForResponse(OutputStream outputStream, InputStream inputStream) {
        //String filename;
        //try {
        //    filename = URLEncoder.encode(fileName, "utf-8");
        //} catch (UnsupportedEncodingException e) {
        //    throw new RuntimeException("，fileName【" + fileName + "】编码失败。");
        //}
        // 下载设置
        //response.setContentType("application/vnd.ms-excel;charset=utf-8");
        //response.setHeader("Content-Disposition", "attachment;filename=" + filename);
        try {
            byte[] bytes = new byte[1024];
            int len;
            while ((len = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
            }
            return outputStream;
        } catch (IOException ioException) {
            throw new RuntimeException("，文件写入失败", ioException);
        }
    }
}
