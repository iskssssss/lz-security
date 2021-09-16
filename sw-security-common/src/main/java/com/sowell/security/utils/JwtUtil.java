package com.sowell.security.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @Version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/08/23 10:02
 */
@SuppressWarnings("unchecked")
public class JwtUtil {
	private static final String SOURCE_CLASS_KEY = "SOURCE_CLASS_TYPE";
	private static final String KEY = "756e3f5b251448f4a0f0dd6e9cb08794";
	private static final byte[] KEY_BYTE_LIST = KEY.getBytes(StandardCharsets.UTF_8);

	/**
	 * 生成Token
	 *
	 * @param t          存储对象
	 * @param expiration 过期时间（毫秒）
	 * @param <T>        存储对象 类型
	 * @return Token
	 */
	public static <T> String generateToken(
			T t,
			Class<T> tClass,
			int expiration
	) {
		final String jsonStr = JSONObject.toJSONString(t);
		final Map<String, Object> map = JSONObject.parseObject(jsonStr, Map.class);
		map.put(SOURCE_CLASS_KEY, tClass);
		Date createdTime = new Date();
		SecretKey key = Keys.hmacShaKeyFor(KEY_BYTE_LIST);
		final JwtBuilder jwtBuilder = Jwts.builder().setClaims(map).setIssuedAt(createdTime).signWith(key);
		if (expiration != -1) {
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(createdTime);
			calendar.add(Calendar.MILLISECOND, expiration);
			Date expirationTime = calendar.getTime();
			jwtBuilder.setExpiration(expirationTime);
		}
		return jwtBuilder.compact();
	}

	/**
	 * 生成Token(没有过期时间)
	 *
	 * @param t   存储对象
	 * @param <T> 存储对象 类型
	 * @return Token
	 */
	public static <T> String generateToken(
			T t,
			Class<T> tClass
	) {
		return generateToken(t, tClass, -1);
	}

	/**
	 * 将Token转换为 T
	 *
	 * @param token  token
	 * @param tClass 目标类
	 * @param <T>    目标类
	 * @return T
	 */
	public static <T> T toBean(
			String token,
			Class<T> tClass
	) {
		Optional<Claims> claimsOptional = Optional.ofNullable(toClaims(token));
		if (!claimsOptional.isPresent()) {
			return null;
		}
		final Claims claims = claimsOptional.get();
		return BeanUtil.toBean(claims, tClass);
	}

	/**
	 * 将Token转换为 T
	 *
	 * @param token token
	 * @param <T>   目标类
	 * @return T
	 */
	public static <T> T toBean(
			String token
	) {
		Optional<Claims> claimsOptional = Optional.ofNullable(toClaims(token));
		if (!claimsOptional.isPresent()) {
			return null;
		}
		final Claims claims = claimsOptional.get();
		final String sourceClass = claims.get(SOURCE_CLASS_KEY, String.class);
		final Optional<Class<?>> sourceClassOptional = Optional.ofNullable(forName(sourceClass));
		return sourceClassOptional
				.map(tClass -> BeanUtil.toBean(claims, (Class<T>) tClass))
				.orElse(null);
	}

	/**
	 * 将Token转换为Claims
	 *
	 * @param token token
	 * @return Claims
	 */
	public static Claims toClaims(
			String token
	) {
		try {
			return Jwts.parser().setSigningKey(KEY_BYTE_LIST).parseClaimsJws(token).getBody();
		} catch (Exception exception) {
			return null;
		}
	}

	/**
	 * Token是否过期
	 *
	 * @param token Token
	 * @return 是否过期 true 过期 false 未过期
	 */
	public static Boolean checkExpiration(
			String token
	) {
		Optional<Claims> claimsOptional = Optional.ofNullable(toClaims(token));
		if (!claimsOptional.isPresent()) {
			return true;
		}
		final Claims claims = claimsOptional.get();
		final Date expiration = claims.getExpiration();
		if (expiration == null) {
			return false;
		}
		return expiration.before(DateUtil.now());
	}

	/**
	 * 通过lambda方式直接过去token中的值
	 *
	 * @param token     token
	 * @param sFunction lambda get表达式
	 * @param <T>       源类型
	 * @param <K>       值类型
	 * @return 值
	 */
	public static <T, K> K getValue(
			String token,
			SFunction<T, K> sFunction
	) {
		final Optional<SerializedLambda> serializedLambdaOptional = Optional.ofNullable(toSerializedLambda(sFunction));
		if (!serializedLambdaOptional.isPresent()) {
			return null;
		}

		// lambda表达式解析
		final SerializedLambda serializedLambda = serializedLambdaOptional.get();
		final Optional<Class<?>> aClassOptional = Optional.ofNullable(forName(serializedLambda.getImplMethodSignature()));
		final Class<?> aClass = aClassOptional.orElse(Object.class);
		String implMethodName = serializedLambda.getImplMethodName().replace("get", "");
		final String oneLowerCase = implMethodName.substring(0, 1).toLowerCase();
		implMethodName = oneLowerCase + implMethodName.substring(1);

		// token解析
		Optional<Claims> claimsOptional = Optional.ofNullable(toClaims(token));
		if (!claimsOptional.isPresent()) {
			return null;
		}
		final Claims claims = claimsOptional.get();

		final String jsonStr = JSONObject.toJSONString(claims);
		final Map<String, Object> map = JSONObject.parseObject(jsonStr, Map.class);

		final Optional<Object> valueOptional = Optional.ofNullable(map.get(implMethodName));
		if (!valueOptional.isPresent()) {
			return null;
		}
		final Object value = valueOptional.get();
		final Object handlerValue = handlerValue(value, aClass);
		return (K) handlerValue;
	}

	/**
	 * 将SFunction转换为SerializedLambda
	 *
	 * @param function lambda表达式
	 * @param <T>      类型
	 * @return SerializedLambda
	 */
	private static <T> SerializedLambda toSerializedLambda(
			SFunction<T, ?> function
	) {
		try {
			final Method writeReplace = function.getClass().getDeclaredMethod("writeReplace");
			writeReplace.setAccessible(true);
			final Object invoke = writeReplace.invoke(function);
			return ((SerializedLambda) invoke);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 处理数据
	 *
	 * @param data   数据
	 * @param tClass 返回类型
	 * @param <T>    返回类型
	 * @return 处理后的数据
	 */
	private static <T> T handlerValue(
			Object data,
			Class<T> tClass
	) {
		if (StringUtil.isEmpty(data)) {
			return null;
		}
		if (
				data instanceof String || data instanceof Boolean ||
						data instanceof Number || data instanceof JSONArray
		) {
			return (T) data;
		}
		final JSONObject dataJson = ((JSONObject) data);
		return JSONObject.parseObject(JSONObject.toJSONString(dataJson), tClass);
	}

	/**
	 * 加载类
	 *
	 * @param className 类名
	 * @return 类
	 */
	public static Class<?> forName(
			String className
	) {
		try {
			final String handlerClassName = className
					.replaceAll("\\(\\)L", "")
					.replaceAll("/", ".")
					.replaceAll(";", "");
			return Class.forName(handlerClassName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
}
