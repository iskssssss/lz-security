[TOC]



## 前言

木有前言。

## 引用

#### maven

```xml
<dependency>
    <groupId>com.sowell.security</groupId>
    <artifactId>lz-security-filter-spring-boot-starter</artifactId>
    <version>1.1.33</version>
</dependency>
```

## 总体配置

#### application.yml

```yml
lz:
  security:
    # [非必需] 是否打印日志信息(默认true)
    console-log-print: true
    # [非必需] AccessToken在请求头中的标识(默认Authorization)
    header-name: Authorization
    # AccessToken相关配置
    access-token-config:
      # [非必需] 类型(默认UUID)(UUID、JWT)(可通过重写IAccessTokenHandler.class来实现自定义token类型,重写后此项将无效)
      access-token-type: UUID
      # [非必需] 过期时间(秒)(默认3600秒)
      timeout: 32
    # 加解密相关配置
    encrypt-config:
      # [非必需] 是否加解密(默认false)
      encrypt: true
      # [非必需] 加密接口列表（逗号(,)隔开）例：/a/**,/b 或 /a/**, /b
      #encrypt-url-list:
      # [未重写请求加解密处理器时该项为必需] 公钥
      public-key: 321
      # [未重写请求加解密处理器时该项为必需] 私钥
      private-key: 123
    # [非必需] Controller类所存放包位置列表（逗号(,)隔开）
    #controller-method-scan-path-list:

```

#### Java

```java
@Configuration
public class SecurityConfig
		extends SecurityConfigurerAdapter {

	@Override
	protected void config(FilterConfigurer filterConfigurer) {
		filterConfigurer
				.filterUrl()
				// 拦截的接口
				.addIncludeUrls("/**")
				// 排除的接口
				.addExcludeUrls(
						"/favicon.ico", "/webjars/**", "/doc.html",
						"/swagger-resources", "/v2/api-docs", "/v2/api-docs-ext",
						"/auth/**"
				).and()
				// 设置接口过滤执行链
				.linkInterfacesFilter(new TestInterfacesFilter())
				// [非必需] 设置过滤日志处理器
				//.filterLogHandler(null)
				// [非必需] 设置过滤错误处理器
				//.filterErrorHandler(null)
				// [非必需] 设置缓存管理器
				//.cacheManager(null)
				// [非必需] 设置AccessToken处理器
				//.accessTokenHandler(null)
				// [非必需] 设置请求加解密处理器(如有特殊需求可重写此处理器)
				//.dataEncoder(null)
				// [非必需] 过滤前处理
				.filterBeforeHandler(params -> {...})
				// [非必需] 过滤后处理
				.filterAfterHandler(params -> {...}).end();
	}
}
```

> 重写 `请求加解密处理器(RequestDataEncryptHandler.class)`可自定义加解密方式。

## 处理/管理器

##### 过滤日志处理器(BaseFilterLogHandler.class)

```java
/**
 * 过滤日志处理器
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @date 2021/7/12 10:39
 */
public interface BaseFilterLogHandler {

    /**
     * 过滤前日志记录
     *
     * @param request  请求信息
     * @param response 响应信息
     * @return 返回对象 用于{@link BaseFilterLogHandler#afterHandler(BaseRequest, BaseResponse, Object, Exception)}方法使用
     */
    Object beforeHandler(BaseRequest<?> request, BaseResponse<?> response);

    /**
     * 过滤后日志记录
     *
     * @param request   请求信息
     * @param response  响应信息
     * @param ex        错误信息
     * @param logEntity 由{@link BaseFilterLogHandler#beforeHandler(BaseRequest, BaseResponse)}方法提供
     */
    void afterHandler(BaseRequest<?> request, BaseResponse<?> response, Object logEntity, Exception ex);
}
```

##### 过滤错误处理器(BaseFilterErrorHandler.class)

```java
/**
 * 过滤错误处理器
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @date 2021/7/14 9:10
 */
public interface BaseFilterErrorHandler<T> {

    /**
     * 错误处理
     *
     * @param request  请求流
     * @param response 响应流
     * @param error    错误信息
     * @return 要打印至客户端的信息
     */
    T errorHandler(BaseRequest<?> request, BaseResponse<?> response, Exception error);
}

```

##### AccessToken处理器(IAccessTokenHandler.class)

```java
/**
 * AccessToken处理器
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @date 2021/10/26 17:13
 */
public interface IAccessTokenHandler {

	/**
	 * 获取获取客户端的AccessToken
	 *
	 * @return AccessToken
	 */
	default String getAccessToken() {
		final BaseRequest<?> servletRequest = IcpSecurityContextThreadLocal.getServletRequest();
		final IcpConfig icpConfig = IcpManager.getIcpConfig();
		final String headerName = icpConfig.getHeaderName();
		final String accessToken = servletRequest.getHeader(headerName);
		if (StringUtil.isEmpty(accessToken)) {
			throw new HeaderNotAccessTokenException();
		}
		return accessToken;
	}

	/**
	 * 生成AccessToken
	 *
	 * @param authDetails 认证信息
	 * @param <T>         类型
	 * @return AccessToken
	 */
	<T extends AuthDetails<T>> String generateAccessToken(AuthDetails<T> authDetails);

	/**
	 * 获取认证信息
	 *
	 * @param accessToken AccessToken
	 * @param <T>         类型
	 * @return 认证信息
	 */
	<T extends AuthDetails<T>> AuthDetails<T> getAuthDetails(String accessToken);

	/**
	 * 获取认证信息
	 *
	 * @param <T> 类型
	 * @return 认证信息
	 */
	<T extends AuthDetails<T>> AuthDetails<T> getAuthDetails();

	/**
	 * 设置认证信息
	 *
	 * @param authDetails 认证信息
	 * @param <T>         类型
	 */
	<T extends AuthDetails<T>> void setAuthDetails(AuthDetails<T> authDetails);
}
```

##### 请求加解密处理器(RequestDataEncryptHandler.class)

```java
/**
 * 数据加解密处理器
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @date 2021/10/26 17:13
 */
public interface RequestDataEncryptHandler {

	/**
	 * 加密
	 *
	 * @param bytes 待加密字节数组
	 * @return 密文
	 */
	String encrypt(byte[] bytes);

	/**
	 * 解密
	 *
	 * @param bytes 带解密字节数组
	 * @return 明文
	 */
	Object decrypt(byte[] bytes);
}
```

##### 缓存管理器(BaseCacheManager.class)

```java
/**
 * 缓存管理器
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @date 2021/08/18 08:43
 */
public interface BaseCacheManager {

	/**
	 * 增加缓存
	 *
	 * @param key   键
	 * @param value 值
	 * @return 结果
	 */
	boolean put(String key, Object value);

	/**
	 * 增加缓存
	 *
	 * @param key     键
	 * @param value   值
	 * @param timeout 过期（毫秒）
	 * @return 结果
	 */
	boolean put(String key, Object value, long timeout);

	/**
	 * 移除缓存
	 *
	 * @param key 键
	 * @return 结果
	 */
	boolean remove(String key);

	/**
	 * 获取缓存
	 *
	 * @param key 键
	 * @return 结果
	 */
	Object get(String key);

	/**
	 * 是否存在键
	 *
	 * @param key 键
	 * @return 是否存在
	 */
	Boolean existKey(String key);
}
```

### 执行链配置

#### 基础类说明

```java
/**
 * 接口过滤执行链
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @date 2021/7/7 11:26
 */
public abstract class AbstractInterfacesFilter extends InterfacesFilterBuilder {

    /**
     * 过滤器初始化
     */
    public abstract void init();

    /**
     * 进行过滤
     *
     * @param request  请求流
     * @param response 响应流
     * @param params   过滤参数
     * @return 过滤结果
     * @throws SecurityException 过滤错误
     */
    public abstract boolean doFilter(BaseRequest<?> request, BaseResponse<?> response, Object... params) throws SecurityException;

    /**
     * 销毁
     */
    public abstract void destroy();
}
```

```java
/**
 * SpringBoot基础执行链
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @date 2021/09/17 16:55
 */
public abstract class SwInterfacesFilter extends AbstractInterfacesFilter {

	@Override
	public final boolean doFilter(
			BaseRequest<?> request,
			BaseResponse<?> response,
			Object... params
	) throws SecurityException {
		return doFilter(((SwRequest) request), ((SwResponse) response), params);
	}

	/**
	 * 进行过滤
	 *
	 * @param swRequest  请求流
	 * @param swResponse 响应流
	 * @param params     过滤参数
	 * @return 过滤结果
	 * @throws SecurityException 过滤错误
	 */
	public abstract boolean doFilter(
			SwRequest swRequest,
			SwResponse swResponse,
			Object... params
	) throws SecurityException;
}
```

##### 示例

```java
/**
 * 测试执行链
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @date 2021/10/22 17:33
 */
public class TestInterfacesFilter extends SwInterfacesFilter {
	@Override
	public boolean doFilter(SwRequest swRequest, SwResponse swResponse, Object... params) {
		IcpLoggerUtil.info(this.getClass(), "自定义过滤器 - 过滤");
		return super.next(swRequest, swResponse, params);
	}

	@Override
	public void init() {
		IcpLoggerUtil.info(this.getClass(), "自定义过滤器 - 初始化");
	}

	@Override
	public void destroy() {
		IcpLoggerUtil.info(this.getClass(), "自定义过滤器 - 销毁");
	}
}
```

#### 框架预设的过滤器

预设的过滤器都可在`FilterUtil`类下进行选择。

##### AccessToken过滤器(AccessTokenFilter)

使用方法：

```java
/**
 * AccessToken过滤器
 * <p>进入此过滤器会先记录日志</p>
 * <p>此过滤器可以过滤未携带有效AccessToken的请求</p>
 *
 * @return AccessToken过滤器
 */
public static AccessTokenFilter accessTokenFilterOnLogBefore() {
	return accessTokenFilter(false, true);
}

/**
 * AccessToken过滤器
 * <p>此过滤器可以过滤未携带有效AccessToken的请求</p>
 *
 * @return AccessToken过滤器
 */
public static AccessTokenFilter accessTokenFilter() {
	return accessTokenFilter(false, false);
}

/**
 * AccessToken过滤器
 * <p>此过滤器可以过滤未携带有效AccessToken的请求</p>
 *
 * @param end 成功是否直接返回，不进行下一步过滤
 * @return AccessToken过滤器
 */
public static AccessTokenFilter accessTokenFilter(boolean end) {
	return accessTokenFilter(end, false);
}

/**
 * AccessToken过滤器
 * <p>此过滤器可以过滤未携带有效AccessToken的请求</p>
 *
 * @param end       成功是否直接返回，不进行下一步过滤
 * @param logBefore 过滤前是否记录日志
 * @return AccessToken过滤器
 */
public static AccessTokenFilter accessTokenFilter(boolean end, boolean logBefore) {
	if (accessTokenFilter == null) {
		if (logBefore) {
			accessTokenFilter = new AccessTokenFilterOnLogBeforeFilter(end);
		} else {
			accessTokenFilter = new AccessTokenFilter(end);
		}
	}
	return accessTokenFilter;
}
```

##### ip过滤器(IpFilter)

使用方法：

```java
/**
 * ip过滤器
 * <p>进入此过滤器会先记录日志</p>
 * <p>此过滤器可排除非限定Ip之外的接口</p>
 *
 * @param ipListGetFun ip列表获取方法
 * @return ip过滤器
 */
public static IpFilter ipFilterOnLogBefore(LambdaFunctional<List<String>> ipListGetFun) {
	return ipFilter(ipListGetFun, false, true);
}
/**
 * ip过滤器
 * <p>此过滤器可排除非限定Ip之外的接口</p>
 *
 * @param ipListGetFun ip列表获取方法
 * @return ip过滤器
 */
public static IpFilter ipFilter(LambdaFunctional<List<String>> ipListGetFun) {
	return ipFilter(ipListGetFun, false);
}
/**
 * ip过滤器
 * <p>此过滤器可排除非限定Ip之外的接口</p>
 *
 * @param ipListGetFun ip列表获取方法
 * @param end          成功是否直接返回，不进行下一步过滤
 * @return ip过滤器
 */
public static IpFilter ipFilter(LambdaFunctional<List<String>> ipListGetFun, boolean end) {
	return ipFilter(ipListGetFun, end, false);
}
/**
 * ip过滤器
 * <p>此过滤器可排除非限定Ip之外的接口</p>
 *
 * @param ipListGetFun ip列表获取方法
 * @param end          成功是否直接返回，不进行下一步过滤
 * @param logBefore    过滤前是否记录日志
 * @return ip过滤器
 */
public static IpFilter ipFilter(LambdaFunctional<List<String>> ipListGetFun, boolean end, boolean logBefore) {
	if (ipFilter == null) {
		if (logBefore) {
			ipFilter = new IpFilterOnLogBeforeFilter(ipListGetFun, end);
		} else {
			ipFilter = new IpFilter(ipListGetFun, end);
		}
	}
	return ipFilter;
}
```

##### 请求接口过滤器(RequestInterfaceFilter)

使用方法：

```java
/**
 * 请求接口过滤器
 * <p>进入此过滤器会先记录日志</p>
 * <p>此过滤器可以限定客户端可访问的接口</p>
 *
 * @param interfaceListGetFun 接口列表获取方法
 * @return 请求接口过滤器
 */
public static RequestInterfaceFilter requestInterfaceFilterOnLogBefore(LambdaFunctional<UrlHashSet> interfaceListGetFun) {
	return requestInterfaceFilter(interfaceListGetFun, false, true);
}

/**
 * 请求接口过滤器
 * <p>此过滤器可以限定客户端可访问的接口</p>
 *
 * @param interfaceListGetFun 接口列表获取方法
 * @return 请求接口过滤器
 */
public static RequestInterfaceFilter requestInterfaceFilter(LambdaFunctional<UrlHashSet> interfaceListGetFun) {
	return requestInterfaceFilter(interfaceListGetFun, false);
}

/**
 * 请求接口过滤器
 * <p>此过滤器可以限定客户端可访问的接口</p>
 *
 * @param interfaceListGetFun 接口列表获取方法
 * @param end                 成功是否直接返回，不进行下一步过滤
 * @return 请求接口过滤器
 */
public static RequestInterfaceFilter requestInterfaceFilter(LambdaFunctional<UrlHashSet> interfaceListGetFun, boolean end) {
	return requestInterfaceFilter(interfaceListGetFun, end, false);
}

/**
 * 请求接口过滤器
 * <p>此过滤器可以限定客户端可访问的接口</p>
 *
 * @param interfaceListGetFun 接口列表获取方法
 * @param end                 成功是否直接返回，不进行下一步过滤
 * @param logBefore           过滤前是否记录日志
 * @return 请求接口过滤器
 */
public static RequestInterfaceFilter requestInterfaceFilter(LambdaFunctional<UrlHashSet> interfaceListGetFun, boolean end, boolean logBefore) {
	if (requestInterfaceFilter == null) {
		if (logBefore) {
			requestInterfaceFilter = new RequestInterfaceFilterOnLogBeforeFilter(interfaceListGetFun, end);
		} else {
			requestInterfaceFilter = new RequestInterfaceFilter(interfaceListGetFun, end);
		}
	}
	return requestInterfaceFilter;
}
```

### 日志配置

本框架通过注解(@LogBeforeFilter)的方式来开启日志记录。

#### LogBeforeFilter注解

该注解只可放在过滤器(AbstractInterfacesFilter.class / SwInterfacesFilter.class)上。放置后会在过滤前进行日志记录。

#### 日志处理

可通过重写BaseFilterLogHandler.class来实现实际业务处理。

### 请求加解密

在配置文件中开启是否加解密。开启加密后还需在配置文件中设置加密接口列表(在方法或者类上添加@ResponseDataEncrypt注解也可)，不设置的话依旧不会加解密。

通过重写RequestDataEncryptHandler.class可实现自定义加解密处理。









