package cn.lz.tool.jwt.model;

/**
 * 用户认证实体类 默认实现类
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2022 LZJ
 * @date 2022/6/30 10:45
 */
public class AuthDetailsDefault extends AuthDetails<AuthDetailsDefault> {
    @Override
    public Class<AuthDetailsDefault> setSourceClass() {
        return AuthDetailsDefault.class;
    }
}
