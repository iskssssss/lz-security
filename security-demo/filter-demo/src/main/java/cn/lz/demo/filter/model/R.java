package cn.lz.demo.filter.model;

import cn.lz.tool.core.enums.RCode;

import java.io.Serializable;

/**
 * TODO
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2022 LZ
 * @date 2022/6/1 14:04
 */
public class R<T> implements Serializable {
    static final long serialVersionUID = 42L;

    private Integer code;
    private String msg;
    private T data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> R<T> success(T t) {
        R<T> r = new R<>();
        r.setCode(200);
        r.setData(t);
        return r;
    }

    public static R<Object> error(RCode errorParams) {
        R<Object> r = new R<>();
        r.setCode(errorParams.getCode());
        r.setMsg(errorParams.getMessage());
        r.setData(null);
        return r;
    }
}
