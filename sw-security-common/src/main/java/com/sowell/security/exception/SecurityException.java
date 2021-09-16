package com.sowell.security.exception;

/**
 * @Version 版权 Copyright(c)2021 浙江设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/7/14 14:11
 */
public class SecurityException extends RuntimeException {

    private Object responseData;

    public SecurityException(String message) {
        super(message);
        this.responseData = null;
    }

    public SecurityException(Object responseData) {
        super("");
        this.responseData = responseData;
    }

    public SecurityException(String message, Object responseData) {
        super(message);
        this.responseData = responseData;
    }

    public SecurityException(String message, Throwable cause){
        super(message, cause);
    }


    public Object getResponseData() {
        return responseData;
    }

    public void setResponseData(Object responseData) {
        this.responseData = responseData;
    }
}
