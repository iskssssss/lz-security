package cn.lz.security.auth.config;

import cn.lz.security.arrays.UrlHashSet;

/**
 * 认证配置
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2022 杭州设维信息技术有限公司
 * @date 2022/7/1 16:38
 */
public class AuthConfig {

    /**
     * 匿名URL列表
     */
    UrlHashSet anonymousUrlList = new UrlHashSet();

    /**
     * 需认证URL列表
     */
    UrlHashSet authUrlList = new UrlHashSet();

    /**
     * 获取匿名URL列表
     *
     * @return 匿名URL列表
     */
    public UrlHashSet getAnonymousUrlList() {
        return this.anonymousUrlList;
    }

    /**
     * 设置匿名URL
     *
     * @param anonymousUrlList 匿名URL
     */
    public void setAnonymousUrlList(UrlHashSet anonymousUrlList) {
        this.anonymousUrlList.addAll(anonymousUrlList);
    }

    /**
     * 获取需认证URL列表
     *
     * @return 需认证URL列表
     */
    public UrlHashSet getAuthUrlList() {
        return this.authUrlList;
    }

    /**
     * 设置需认证URL
     *
     * @param authUrlList 需认证URL
     */
    public void setAuthUrlList(UrlHashSet authUrlList) {
        this.authUrlList.addAll(authUrlList);
    }

    public String print() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n          ").append("• 需认证URL列表").append("：");
        for (String auth : this.authUrlList) {
            sb.append("\n          ").append("    - ").append(auth);
        }
        sb.append("\n          ").append("• 匿名URL列表").append("：");
        for (String anonymous : this.anonymousUrlList) {
            sb.append("\n          ").append("    - ").append(anonymous);
        }
        return sb.toString();
    }
}
