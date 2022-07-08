package cn.lz.security.config;

import cn.lz.tool.core.ArraysUtil;
import cn.lz.tool.core.string.StringUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * 跨域配置
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2022 杭州设维信息技术有限公司
 * @date 2022/6/28 14:59
 */
public class CorsConfig {

    private List<CorsRegistration> corsRegistrationList;

    public List<CorsRegistration> getCorsRegistrationList() {
        if (corsRegistrationList == null) {
            corsRegistrationList = new LinkedList<>();
        }
        return corsRegistrationList;
    }

    public void setCorsRegistrationList(List<CorsRegistration> corsRegistrationList) {
        this.corsRegistrationList = corsRegistrationList;
    }

    public static class CorsRegistration {
        private String pathPattern;

        private CorsItemConfig config;

        public String getPathPattern() {
            return pathPattern;
        }

        public void setPathPattern(String pathPattern) {
            this.pathPattern = pathPattern;
        }

        public CorsItemConfig getConfig() {
            return config;
        }

        public void setConfig(CorsItemConfig config) {
            this.config = config;
        }
    }

    public static class CorsItemConfig {

        public static final String ALL = "*";

        private static final List<String> DEFAULT_METHODS = Collections.unmodifiableList(Arrays.asList("GET", "HEAD"));

        private static final List<String> DEFAULT_PERMIT_METHODS = Collections.unmodifiableList(Arrays.asList("GET", "HEAD", "POST"));

        private static final List<String> DEFAULT_PERMIT_ALL = Collections.unmodifiableList(Collections.singletonList(ALL));

        private List<String> allowedOrigins;

        private List<String> allowedMethods;

        private List<String> resolvedMethods = DEFAULT_METHODS;

        private List<String> allowedHeaders;

        private List<String> exposedHeaders;

        private Boolean allowCredentials;

        private Long maxAge;

        public List<String> getAllowedOrigins() {
            return allowedOrigins;
        }

        public void setAllowedOrigins(List<String> allowedOrigins) {
            this.allowedOrigins = allowedOrigins;
        }

        public List<String> getAllowedMethods() {
            return allowedMethods;
        }

        public void setAllowedMethods(List<String> allowedMethods) {
            this.allowedMethods = allowedMethods;
        }

        public List<String> getResolvedMethods() {
            return resolvedMethods;
        }

        public void setResolvedMethods(List<String> resolvedMethods) {
            this.resolvedMethods = resolvedMethods;
        }

        public List<String> getAllowedHeaders() {
            return allowedHeaders;
        }

        public void setAllowedHeaders(List<String> allowedHeaders) {
            this.allowedHeaders = allowedHeaders;
        }

        public List<String> getExposedHeaders() {
            return exposedHeaders;
        }

        public void setExposedHeaders(List<String> exposedHeaders) {
            this.exposedHeaders = exposedHeaders;
        }

        public Boolean getAllowCredentials() {
            return allowCredentials;
        }

        public void setAllowCredentials(Boolean allowCredentials) {
            this.allowCredentials = allowCredentials;
        }

        public Long getMaxAge() {
            return maxAge;
        }

        public void setMaxAge(Long maxAge) {
            this.maxAge = maxAge;
        }

        public String checkOrigin(String requestOrigin) {
            if (StringUtil.isEmpty(requestOrigin)) {
                return null;
            }
            if (ArraysUtil.isEmpty(this.allowedOrigins)) {
                return null;
            }

            if (this.allowedOrigins.contains(ALL)) {
                if (this.allowCredentials != Boolean.TRUE) {
                    return ALL;
                }
                return requestOrigin;
            }
            for (String allowedOrigin : this.allowedOrigins) {
                if (requestOrigin.equalsIgnoreCase(allowedOrigin)) {
                    return requestOrigin;
                }
            }
            return null;
        }
    }
}
