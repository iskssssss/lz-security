package com.sowell.security.utils;

import cn.hutool.core.io.FileTypeUtil;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * @version 版权 Copyright(c)2021 浙江设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @author: 孔胜
 * @date: 2021/5/12 10:14
 */
public class FileUtil extends cn.hutool.core.io.FileUtil {

    /**
     * 将文件写入Response后关闭输出流
     *
     * @param response 响应
     * @param path     文件路径
     * @param fileName 下载文件名称
     * @return 输出流
     */
    public static String writeFileForResponseClose(
            HttpServletResponse response,
            String path,
            String fileName
    ) {
        try (
                final BufferedInputStream inputStream = FileUtil.getInputStream(path)
        ) {
            FileUtil.writeFileForResponseClose(response, inputStream, fileName);
            return null;
        } catch (IOException ioException) {
            return "，文件写入失败";
        }
    }

    /**
     * 将文件写入Response后关闭输出流
     *
     * @param response    响应
     * @param inputStream 输入流
     * @param fileName    下载文件名称
     * @return 是否写入成功
     */
    public static String writeFileForResponseClose(
            HttpServletResponse response,
            InputStream inputStream,
            String fileName
    ) {
        String filename;
        try {
            filename = URLEncoder.encode(fileName, "utf-8");
        } catch (UnsupportedEncodingException e) {
            return "，fileName【" + fileName + "】编码失败。";
        }
        // 下载设置
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + filename);

        try (
                final OutputStream outputStream = response.getOutputStream()
        ) {
            byte[] bytes = new byte[1024];
            int len;
            while ((len = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
            }
            return null;
        } catch (IOException ioException) {
            return "，文件写入失败";
        }
    }

    /**
     * 将文件写入Response后不关闭输出流
     *
     * @param response 响应
     * @param path     文件路径
     * @param fileName 下载文件名称
     * @return 输出流
     */
    public static OutputStream writeFileForResponse(
            HttpServletResponse response,
            String path,
            String fileName
    ) {
        try (
                final BufferedInputStream inputStream = FileUtil.getInputStream(path)
        ) {
            return FileUtil.writeFileForResponse(response, inputStream, fileName);
        } catch (IOException ioException) {
            throw new RuntimeException("，文件写入失败", ioException);
        }
    }

    /**
     * 将文件写入Response后不关闭输出流
     *
     * @param response    响应
     * @param inputStream 输入流
     * @param fileName    下载文件名称
     * @return 输出流
     */
    public static OutputStream writeFileForResponse(
            HttpServletResponse response,
            InputStream inputStream,
            String fileName
    ) {
        String filename;
        try {
            filename = URLEncoder.encode(fileName, "utf-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("，fileName【" + fileName + "】编码失败。");
        }
        // 下载设置
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + filename);
        try {
            final OutputStream outputStream = response.getOutputStream();
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

    /**
     * 获取文件后缀
     *
     * @param file 文件
     * @return 文件后缀
     * @throws IOException 流异常
     */
    public static String getFileType(MultipartFile file) throws IOException {
        final String originalFilename = file.getOriginalFilename();
        String fileType = FileTypeUtil.getType(file.getInputStream());
        if (StringUtils.hasText(fileType)) {
            return fileType;
        }
        if (StringUtils.isEmpty(originalFilename)) {
            return null;
        }
        final String[] fileNameSplit = originalFilename.split("\\.");
        if (fileNameSplit.length < 2) {
            return null;
        }
        return fileNameSplit[fileNameSplit.length - 1];
    }
}
