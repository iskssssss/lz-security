package com.sowell.security.service;


import com.sowell.tool.jwt.model.AuthDetails;

/**
 * @Version 版权 Copyright(c)2021 浙江设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/6/23 17:44
 */
public interface UserDetailsService {

    AuthDetails<?> readUserByUsername(String username) throws SecurityException;
}
