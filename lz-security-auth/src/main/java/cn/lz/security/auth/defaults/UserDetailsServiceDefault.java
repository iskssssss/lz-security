package cn.lz.security.auth.defaults;

import cn.lz.security.auth.service.UserDetailsService;
import cn.lz.tool.jwt.model.AuthDetails;
import cn.lz.tool.jwt.model.AuthDetailsDefault;

/**
 * 用户认证信息获取服务类 默认实现
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2022 LZJ
 * @date 2022/6/30 10:43
 */
public class UserDetailsServiceDefault implements UserDetailsService {

    @Override
    public AuthDetails<?> readUserByIdentifier(String userId) throws SecurityException {
        AuthDetails<AuthDetailsDefault> authDetails = new AuthDetailsDefault();
        authDetails.setId(userId);
        return authDetails;
    }
}
