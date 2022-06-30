package cn.lz.security.auth.service;


import cn.lz.tool.jwt.model.AuthDetails;

/**
 * 用户认证信息获取服务类
 * @Version 版权 Copyright(c)2021 LZ
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/6/23 17:44
 */
public interface UserDetailsService {

    /**
     * 根据用户id获取用户信息
     *
     * @param userId 用户id
     * @return 用户信息
     * @throws SecurityException 获取中发生的异常
     */
    AuthDetails<?> readUserByUserId(String userId) throws SecurityException;
}
