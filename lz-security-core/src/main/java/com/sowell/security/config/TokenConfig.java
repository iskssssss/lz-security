package com.sowell.security.config;

import com.sowell.security.LzConstant;
import com.sowell.tool.core.string.StringUtil;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * token配置
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2022 杭州设维信息技术有限公司
 * @date 2022/6/1 10:16
 */
public class TokenConfig {
    /**
     * Token 存放标识
     */
    private String name = "Authorization";
    /**
     * Token 前缀
     */
    private String prefix = "";
    /**
     * Token 默认过期时间(3600秒)
     */
    private static final long DEFAULT_TIMEOUT = 3600L;
    /**
     * Token 类型（uuid, jwt）
     */
    private String type = "UUID";
    /**
     * Token 过期时间（秒）(默认3600秒)
     */
    private Long timeout = 3600L;

    /**
     * 获取Token存放标识
     *
     * @return Token存放标识
     */
    public String getName() {
        return this.name;
    }

    /**
     * 设置Token存放标识
     *
     * @param name 标识
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取Token 前缀
     *
     * @return Token 前缀
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * 设置Token 前缀
     *
     * @param prefix Token 前缀
     */
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    /**
     * 获取类型
     *
     * @return 类型
     */
    public String getType() {
        if (StringUtil.isEmpty(this.type)) {
            return LzConstant.TOKEN_TYPE_BY_UUID;
        }
        return this.type.toUpperCase(Locale.ROOT);
    }

    /**
     * 设置类型
     *
     * @param type 类型
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取过期时间（秒）
     *
     * @return 过期时间（秒）
     */
    public long getTimeout() {
        final Long timeout = this.timeout;
        if (timeout == null) {
            return DEFAULT_TIMEOUT;
        }
        return timeout.intValue();
    }

    /**
     * 获取过期时间（毫秒）
     *
     * @return 过期时间（毫秒）
     */
    public long getTimeoutForMillis() {
        final long timeout = getTimeout();
        if (timeout == -1) {
            return timeout;
        }
        return TimeUnit.SECONDS.toMillis(timeout);
    }

    /**
     * 设置过期时间（秒）
     *
     * @param timeout 过期时间（秒）
     */
    public void setTimeout(Long timeout) {
        this.timeout = timeout;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n          ").append("• Token 存放标识").append("：").append(name);
        sb.append("\n          ").append("• Token 前缀").append("：").append(prefix);
        sb.append("\n          ").append("• Token 类型").append("：").append(type);
        sb.append("\n          ").append("• Token 过期时间（秒）").append("：").append(timeout);
        return sb.toString();
    }
}
