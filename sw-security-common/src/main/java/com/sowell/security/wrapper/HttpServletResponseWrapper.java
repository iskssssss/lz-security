package com.sowell.security.wrapper;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

/**
 * @Version 版权 Copyright(c)2021 浙江设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/7/12 16:40
 */
public class HttpServletResponseWrapper extends javax.servlet.http.HttpServletResponseWrapper {

    private final ByteArrayOutputStream bytes;
    private PrintWriter printWriter;

    public HttpServletResponseWrapper(HttpServletResponse response) {
        super(response);

        bytes = new ByteArrayOutputStream();
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return new ServletOutputStream() {
            @Override
            public void write(int b) throws IOException {
                bytes.write(b);
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setWriteListener(WriteListener writeListener) {
            }
        };
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        printWriter = new PrintWriter(new OutputStreamWriter(bytes, StandardCharsets.UTF_8));
        return printWriter;
    }

    /**
     * 获取响应数据
     */
    public byte[] toByteArray() {
        if (null != printWriter) {
            printWriter.close();
            return bytes.toByteArray();
        }
        try {
            bytes.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes.toByteArray();
    }
}
