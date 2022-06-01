package cn.lz.tool.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.IOException;
import java.util.List;
/**
 * @Author: lz
 * @Date: 2021/08/21 11:48
 */
public class JsonUtil {
	private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();
	static {
		OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	/**
	 * 将字符串转为T
	 *
	 * @param jsonStr Json字符串
	 * @param tClass  目标类
	 * @param <T>     目标类泛型
	 * @return T
	 */
	public static <T> T parseObject(String jsonStr, Class<T> tClass) {
		try {
			return OBJECT_MAPPER.readValue(jsonStr, tClass);
		} catch (IOException ioException) {
			return null;
		}
	}

	/**
	 * 将实体转换成Json字符串
	 *
	 * @param object 实体
	 * @return Json字符串
	 */
	public static String toJsonString(Object object) {
		try {
			return OBJECT_MAPPER.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			return "";
		}
	}

	/**
	 * 将Json字符串转换为List< T >
	 *
	 * @param text   Json字符串
	 * @param tClass 目标类
	 * @param <T>    目标类泛型
	 * @return List<T>
	 */
	public static <T> List<T> parseArray(String text, Class<T> tClass) {
		final TypeFactory typeFactory = OBJECT_MAPPER.getTypeFactory();
		final JavaType javaType = typeFactory.constructParametricType(List.class, tClass);
		try {
			return OBJECT_MAPPER.readValue(text, javaType);
		} catch (IOException ioException) {
			return null;
		}
	}
}
