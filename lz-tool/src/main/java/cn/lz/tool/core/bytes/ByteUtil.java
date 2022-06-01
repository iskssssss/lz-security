package cn.lz.tool.core.bytes;

import cn.lz.tool.core.number.NumberUtil;
import cn.lz.tool.core.string.StringUtil;
import cn.lz.tool.io.FastByteArrayOutputStream;
import cn.lz.tool.json.JsonUtil;
import cn.lz.tool.reflect.BeanUtil;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @Version 版权 Copyright(c)2021 LZ
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/10/21 14:19
 */
public class ByteUtil {
	public static final int EOF = -1;

	public static FastByteArrayOutputStream toFastByteArrayOutputStream(InputStream in) {
		final FastByteArrayOutputStream out = new FastByteArrayOutputStream();
		byte[] buffer = new byte[8192];
		try {
			for (int readSize; (readSize = in.read(buffer)) != ByteUtil.EOF; ) {
				out.write(buffer, 0, readSize);
			}
			out.flush();
			return out;
		} catch (IOException e) {
			return null;
		}
	}

	public static byte[] toBytes(Object obj) {
		if (obj instanceof CharSequence || obj instanceof Number) {
			return obj.toString().getBytes(StandardCharsets.UTF_8);
		}
		if (obj instanceof Map) {
			final String jsonStr = JsonUtil.toJsonString(obj);
			if (StringUtil.isEmpty(jsonStr)){
				return null;
			}
			return jsonStr.getBytes(StandardCharsets.UTF_8);
		}
		return object2Bytes(obj);
	}

	public static Object toObject(byte[] bytes) {
		final Object o = bytes2Object(bytes);
		if (o != null) {
			return o;
		}
		return new String(bytes, StandardCharsets.UTF_8);
	}

	public static <T> T toObject(byte[] bytes, Class<T> tClass) {
		final Object obj = toObject(bytes);
		if (obj.getClass() == tClass) {
			return (T) obj;
		}
		if (obj instanceof String) {
			try {
				return JsonUtil.parseObject(((String) obj), tClass);
			} catch (Exception ignored) {
			}
		}
		final T t = BeanUtil.toBean(obj, tClass);
		if (t != null) {
			return t;
		}
		Object res = null;
		final String objStr = obj.toString();
		if (!NumberUtil.checkNumber(objStr)) {
			return (T) obj.toString();
		}
		if (objStr.contains(".")) {
			if (tClass == Double.class) {
				res = Double.parseDouble(objStr);
			}
			if (tClass == Float.class) {
				res = Float.parseFloat(objStr);
			}
		} else {
			if (tClass == Integer.class) {
				res = Integer.parseInt(objStr);
			}
			if (tClass == Long.class) {
				res = Long.parseLong(objStr);
			}
		}
		if (res == null) {
			throw new IllegalArgumentException("数据(" + objStr + " -> " + tClass.getName() + ")转换失败，请确定转换后的数据类型！");
		}
		return (T) res;
	}

	public static byte[] object2Bytes(Object obj) {
		try (ByteArrayOutputStream os = new ByteArrayOutputStream();
		     ObjectOutputStream oos = new ObjectOutputStream(os);) {
			oos.writeObject(obj);
			oos.flush();
			return os.toByteArray();
		} catch (IOException ioException) {
			ioException.printStackTrace();
			return null;
		}
	}

	public static <T> T bytes2Object(byte[] bytes, Class<T> tClass) {
		Object o;
		try {
			o = bytes2Object(bytes);
			if (o == null) {
				return null;
			}
			if (o.getClass() == tClass) {
				return (T) o;
			}
			return BeanUtil.toBean(o, tClass);
		} catch (Exception e) {
			return null;
		}
	}

	public static Object bytes2Object(byte[] bytes) {
		try (ByteArrayInputStream is = new ByteArrayInputStream(bytes);
		     ObjectInputStream ois = new ObjectInputStream(is)) {
			return ois.readObject();
		} catch (IOException | ClassNotFoundException ioException) {
			return null;
		}
	}
}
