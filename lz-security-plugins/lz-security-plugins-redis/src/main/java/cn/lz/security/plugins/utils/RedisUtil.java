package cn.lz.security.plugins.utils;

import cn.lz.security.log.LzLogger;
import cn.lz.security.log.LzLoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Description Redis 工具类(String hash set list )
 * @Author eternity
 * @Date 2020/5/24 19:58
 */
@Component
@SuppressWarnings("unchecked")
public final class RedisUtil {
    private static final LzLogger log = LzLoggerUtil.getLzLogger(RedisUtil.class);

    private static final String ILLEGAL_ARGUMENT_MESSAGE = "参数列表不可有空值.";
    private static final String TIMEOUT_MESSAGE = "过期时间不可为零.";

    private static RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    public RedisUtil(RedisTemplate<Object, Object> redisTemplate) {
        RedisUtil.redisTemplate = redisTemplate;
    }

    /**
     * 观察键
     *
     * @param key 键
     * @return 结果
     */
    public static boolean watch(Object key) {
        try {
            if (!Boolean.TRUE.equals(exists(key))) {
                return false;
            }
            redisTemplate.watch(key);
            return true;
        } catch (Exception e) {
            log.error(String.format("观察键(%s)失败.", key), e);
            return false;
        }
    }

    /**
     * 开启事务
     *
     * @return 结果
     */
    public static boolean multi() {
        try {
            redisTemplate.multi();
            return true;
        } catch (Exception e) {
            log.error("开启事务失败.", e);
            return false;
        }
    }

    /**
     * 结束事务
     *
     * @return 结果
     */
    public static boolean exec() {
        try {
            redisTemplate.exec();
            return true;
        } catch (Exception e) {
            log.error("结束事务失败.", e);
            return false;
        }
    }

    /**
     * 回滚事务
     *
     * @return 结果
     */
    public static boolean discard() {
        try {
            redisTemplate.discard();
            return true;
        } catch (Exception e) {
            log.error("回滚事务失败.", e);
            return false;
        }
    }

    /**
     * 设置过期时间(毫秒)
     *
     * @param key     键
     * @param timeout 过期时间
     * @return 设置结果
     */
    public static Boolean expire(Object key, long timeout) {
        try {
            if (timeout <= 0) {
                throw new IllegalArgumentException(TIMEOUT_MESSAGE);
            }
            return redisTemplate.expire(key, timeout, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 查看键的有效期
     *
     * @param key 键
     * @return 有效期(0为永久有效)
     */
    public static Long ttl(Object key) {
        if (key == null || "".equals(key)) {
            return null;
        }
        return redisTemplate.getExpire(key);
    }

    /**
     * 取消键的过期时间
     *
     * @param key 键
     * @return 结果
     */
    public static Boolean persist(Object key) {
        if (key == null || "".equals(key)) {
            return false;
        }
        return redisTemplate.persist(key);
    }

    /**
     * 是否存在键
     *
     * @param key 键
     * @return 结果
     */
    public static Boolean exists(Object key) {
        if (key == null || "".equals(key)) {
            return false;
        }
        return redisTemplate.hasKey(key);
    }

    /**
     * 普通写入
     *
     * @param key   键
     * @param value 值
     * @return 是否写入成功
     */
    public static boolean set(Object key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            log.error("写入错误.", e);
            return false;
        }
    }

    /**
     * 普通写入并设置过期时间
     *
     * @param key     键
     * @param value   值
     * @param timeout 过期时间(毫秒)
     * @return 是否写入成功
     */
    public static boolean set(Object key, Object value, long timeout) {
        try {
            if (timeout <= 0) {
                throw new IllegalArgumentException(TIMEOUT_MESSAGE);
            }
            redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.MILLISECONDS);
            return true;
        } catch (Exception e) {
            log.error("写入错误.", e);
            return false;
        }
    }

    /**
     * 删除键
     *
     * @param keys 键
     */
    public static void del(Object... keys) {
        if (keys == null || keys.length <= 0) {
            throw new IllegalArgumentException(ILLEGAL_ARGUMENT_MESSAGE);
        }
        if (keys.length == 1) {
            redisTemplate.delete(keys[0]);
        }
        redisTemplate.delete(CollectionUtils.arrayToList(keys));
    }

    /**
     * 普通读取
     *
     * @param key 键
     * @return 值
     */
    public static Object get(Object key) {
        if (key == null || "".equals(key)) {
            return null;
        }
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * @param key
     * @return
     */
    public static Long incr(Object key) {
        try {
            if (key == null || "".equals(key)) {
                return 0L;
            }
            return redisTemplate.opsForValue().increment(key);
        } catch (Exception e) {
            return 0L;
        }
    }

    public static Long incr(Object key, long delta) {
        try {
            if (key == null || "".equals(key)) {
                return 0L;
            }
            return redisTemplate.opsForValue().increment(key, delta);
        } catch (Exception e) {
            return 0L;
        }
    }

    public static Long decr(Object key) {
        try {
            if (key == null || "".equals(key)) {
                return 0L;
            }
            return redisTemplate.opsForValue().decrement(key);
        } catch (Exception e) {
            return 0L;
        }
    }

    public static Long decr(Object key, long delta) {
        try {
            if (key == null || "".equals(key)) {
                return 0L;
            }
            return redisTemplate.opsForValue().decrement(key, delta);
        } catch (Exception e) {
            return 0L;
        }
    }


    /**
     * 设置单个hash键值
     *
     * @param key       键
     * @param hashKey   hash键
     * @param hashValue hash值
     * @return 结果
     */
    public static boolean hashSet(Object key, String hashKey, Object hashValue) {
        try {
            redisTemplate.opsForHash().put(key, hashKey, hashValue);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 设置单个hash键值并设置过期时间
     *
     * @param key       键
     * @param hashKey   hash键
     * @param hashValue hash值
     * @param timeout   过期时间
     * @return 结果
     */
    public static boolean hashSet(Object key, String hashKey, Object hashValue, long timeout) {
        try {
            redisTemplate.opsForHash().put(key, hashKey, hashValue);
            expire(key, timeout);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 设置map
     *
     * @param key 键
     * @param map hash键值
     * @return 结果
     */
    public static boolean hashSet(Object key, Map<String, Object> map) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 设置map并设置过期时间
     *
     * @param key     键
     * @param map     hash键值
     * @param timeout 过期时间
     * @return 结果
     */
    public static boolean hashSet(Object key, Map<String, Object> map, long timeout) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            expire(key, timeout);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 读取
     *
     * @param key     键
     * @param hashKey hash键
     * @return hash值
     */
    public static Object hashGet(Object key, Object hashKey) {
        try {
            if (key == null || hashKey == null) {
                throw new IllegalArgumentException(ILLEGAL_ARGUMENT_MESSAGE);
            }
            return redisTemplate.opsForHash().get(key, hashKey);
        } catch (Exception e) {
            log.error("读取失败.", e);
            return null;
        }
    }

    /**
     * 读取(相应key的所有hash键值)
     *
     * @param key 键
     * @return 键值列表
     */
    public static Map<Object, Object> hashGet(Object key) {
        if (key == null) {
            throw new IllegalArgumentException(ILLEGAL_ARGUMENT_MESSAGE);
        }
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 删除
     *
     * @param key      键
     * @param hashKeys hash键列表
     */
    public static Long hashDel(Object key, Object... hashKeys) {
        if (key == null || hashKeys == null || hashKeys.length <= 0) {
            throw new IllegalArgumentException(ILLEGAL_ARGUMENT_MESSAGE);
        }
        return redisTemplate.opsForHash().delete(key, hashKeys);
    }

    /**
     * 是否存在hash键
     *
     * @param key     键
     * @param hashKey hash键
     */
    public static Boolean hashExists(Object key, String hashKey) {
        if (key == null || hashKey == null) {
            throw new IllegalArgumentException(ILLEGAL_ARGUMENT_MESSAGE);
        }
        return redisTemplate.opsForHash().hasKey(key, hashKey);
    }

    /**
     * 写入
     *
     * @param key    键
     * @param values set值
     * @return 成功数量
     */
    public static Long setAdd(Object key, Object... values) {
        try {
            if (key == null || values == null || values.length <= 0) {
                throw new IllegalArgumentException(ILLEGAL_ARGUMENT_MESSAGE);
            }
            return redisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            log.error("写入错误.", e);
            return 0L;
        }
    }

    /**
     * 写入
     *
     * @param key     键
     * @param timeout 过期时间
     * @param values  set值
     * @return 成功数量
     */
    public static Long setAdd(Object key, long timeout, Object... values) {
        if (key == null || values == null || values.length <= 0) {
            throw new IllegalArgumentException(ILLEGAL_ARGUMENT_MESSAGE);
        }
        Long successNumber = setAdd(key, values);
        if (successNumber <= 0) {
            return 0L;
        }
        expire(key, timeout);
        return successNumber;
    }

    public static Set<Object> setGet(Object key) {
        try {
            if (key == null) {
                throw new IllegalArgumentException(ILLEGAL_ARGUMENT_MESSAGE);
            }
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            log.error("获取失败.", e);
            return null;
        }
    }

    /**
     * 是否存在值
     *
     * @param key   键
     * @param value 值
     * @return 结果
     */
    public static Boolean setExists(Object key, Object value) {
        try {
            return redisTemplate.opsForSet().isMember(key, value);
        } catch (Exception e) {
            log.error("查找错误.", e);
            return false;
        }
    }

    /**
     * 获取set数量
     *
     * @param key 键
     * @return 数量
     */
    public static Long setSize(Object key) {
        try {
            return redisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            log.error("获取错误.", e);
            return 0L;
        }
    }

    /**
     * 删除值
     *
     * @param key    键
     * @param values 值
     * @return 删除结果
     */
    public static Long setRemove(Object key, Object... values) {
        try {
            if (key == null || values == null || values.length <= 0) {
                throw new IllegalArgumentException(ILLEGAL_ARGUMENT_MESSAGE);
            }
            return redisTemplate.opsForSet().remove(key, values);
        } catch (Exception e) {
            log.error("删除错误.", e);
            return 0L;
        }
    }


    /**
     * 添加列表元素
     *
     * @param key    键
     * @param value  值
     * @param isLeft 是否从右边插入
     * @return 插入数量
     */
    public static Long listSet(Object key, Object value, boolean isLeft) {
        try {
            if (isLeft) {
                return redisTemplate.opsForList().leftPush(key, value);
            }
            return redisTemplate.opsForList().rightPush(key, value);
        } catch (Exception e) {
            log.error("设置错误.", e);
            return 0L;
        }
    }

    /**
     * 添加列表元素并设置超时时间
     *
     * @param key     键
     * @param value   值
     * @param timeout 超时时间
     * @param isLeft  是否从右边插入
     * @return 插入数量
     */
    public static Long listSet(Object key, Object value, Long timeout, boolean isLeft) {
        Long successNumber = listSet(key, value, isLeft);
        expire(key, timeout);
        return successNumber;
    }

    /**
     * 批量添加列表元素
     *
     * @param key    键
     * @param values 列表
     * @param isLeft 是否从右边插入
     * @return 插入数量
     */
    public static Long listSet(Object key, List<Object> values, boolean isLeft) {
        try {
            if (isLeft) {
                return redisTemplate.opsForList().leftPushAll(key, values);
            }
            return redisTemplate.opsForList().rightPushAll(key, values);
        } catch (Exception e) {
            log.error("设置错误.", e);
            return 0L;
        }
    }

    /**
     * 批量添加列表元素并设置超时时间
     *
     * @param key     键
     * @param values  列表
     * @param timeout 超时时间
     * @param isLeft  是否从右边插入
     * @return 插入数量
     */
    public static Long listSet(Object key, List<Object> values, Long timeout, boolean isLeft) {
        Long successNumber = listSet(key, values, isLeft);
        expire(key, timeout);
        return successNumber;
    }

    /**
     * 获取单个元素
     *
     * @param key   键
     * @param index 位置(0为第一元素、index < 0 从尾部获取 )
     * @return 值
     */
    public static Object listGet(Object key, Long index) {
        return redisTemplate.opsForList().index(key, index);
    }

    /**
     * 获取指定范围元素
     *
     * @param key   键
     * @param start 开始位置
     * @param end   结束位置
     * @return 元素列表
     */
    public static List<Object> listGet(Object key, Long start, Long end) {
        List<Object> items = new ArrayList<>();
        for (long index = start; index < end; index++) {
            items.add(listGet(key, index));
        }
        return items;
    }

    /**
     * 获取list全部数据
     *
     * @param key 键
     * @return 值
     */
    public static List<Object> listGet(Object key) {
        Long size = listSize(key);
        return listGet(key, 0L, size);
    }

    /**
     * 获取list数量
     *
     * @param key 键
     * @return 数量
     */
    public static Long listSize(Object key) {
        return redisTemplate.opsForList().size(key);
    }

    /**
     * 删除list元素
     *
     * @param key   键
     * @param count 删除个数(0:全部删除 负数:从尾部删除 正数:从头部删除)
     * @param value 值
     * @return 删除数量
     */
    public static Long listRemove(Object key, long count, Object value) {
        try {
            return redisTemplate.opsForList().remove(key, count, value);
        } catch (Exception e) {
            log.error("删除错误.", e);
            return 0L;
        }
    }
}
