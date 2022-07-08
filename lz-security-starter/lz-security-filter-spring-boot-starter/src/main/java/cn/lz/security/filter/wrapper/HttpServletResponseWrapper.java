package cn.lz.security.filter.wrapper;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * @Version 版权 Copyright(c)2021 LZ
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/7/12 16:40
 */
public class HttpServletResponseWrapper extends javax.servlet.http.HttpServletResponseWrapper {

    private final ByteArrayOutputStream buffer;
    private final ServletOutputStream out;
    private final PrintWriter writer;

    public HttpServletResponseWrapper(HttpServletResponse response) throws IOException {
        super(response);
        buffer = new ByteArrayOutputStream();
        out = new HttpServletResponseWrapperOutputStream(buffer);
        writer = new PrintWriter(new OutputStreamWriter(buffer, this.getCharacterEncoding()));
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return out;
    }

    @Override
    public PrintWriter getWriter() {
        return writer;
    }

    public ServletOutputStream getResponseOutputStream() throws IOException {
        return getResponse().getOutputStream();
    }

    public PrintWriter getResponseWriter() throws IOException {
        return getResponse().getWriter();
    }

    @Override
    public void flushBuffer() throws IOException {
        if (out != null) {
            out.flush();
        }
        if (writer != null) {
            writer.flush();
        }
    }

    @Override
    public void reset() {
        buffer.reset();
    }

    public byte[] toByteArray() throws IOException {
        flushBuffer();
        return buffer.toByteArray();
    }

    private static class HttpServletResponseWrapperOutputStream extends ServletOutputStream {
        private final ByteArrayOutputStream bos;

        public HttpServletResponseWrapperOutputStream(ByteArrayOutputStream stream) {
            bos = stream;
        }

        @Override
        public boolean isReady() {
            return false;
        }

        @Override
        public void setWriteListener(WriteListener listener) {

        }

        @Override
        public void write(int b) throws IOException {
            bos.write(b);
        }

        @Override
        public void write(byte[] b) throws IOException {
            bos.write(b, 0, b.length);
        }
    }
}
