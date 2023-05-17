package cn.lz.tool.cache.listener;

/**
 * TODO
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2022 LZJ
 * @date 2022/8/8 13:55
 */
public interface CacheListener <K, V> {

    /**
     * 对象移除回调
     *
     * @param key          键
     * @param cachedObject 被缓存的对象
     */
    void onRemove(K key, V cachedObject);
}
