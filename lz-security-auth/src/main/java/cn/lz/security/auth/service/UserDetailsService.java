package cn.lz.security.auth.service;


import cn.lz.tool.jwt.model.AuthDetails;

/**
 * @Version 版权 Copyright(c)2021 LZ
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/6/23 17:44
 */
public interface UserDetailsService {

    AuthDetails<?> readUserByUsername(String username) throws SecurityException;
}
