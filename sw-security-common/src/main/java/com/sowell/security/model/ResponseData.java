package com.sowell.security.model;

import com.sowell.security.enums.HttpStatus;
import lombok.Data;
import lombok.ToString;

import java.util.Map;

/**
 * @Version 版权 Copyright(c)2021 浙江设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/7/12 14:09
 */
@Data
@ToString
public class ResponseData<T> {

    private String msg;

    private HttpStatus httpStatus;

    private T bodyData;

    private Map<String, String> paramMap;
}
